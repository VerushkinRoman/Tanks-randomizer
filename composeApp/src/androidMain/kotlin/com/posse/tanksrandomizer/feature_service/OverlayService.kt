package com.posse.tanksrandomizer.feature_service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.compositionContext
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.posse.tanksrandomizer.CHANNEL_ID
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
import com.posse.tanksrandomizer.common.presentation.interactor.ScreenSettingsInteractor
import com.posse.tanksrandomizer.feature_service.compose.components.ChangeSizeButton
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.getDisplayCutoutSize
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.getNavigationBarHeight
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.getScreenHeight
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.getScreenWidth
import com.posse.tanksrandomizer.navigation.compose.AndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.system.exitProcess

class OverlayService : Service() {
    private val windowManager by lazy { getSystemService<WindowManager>() }
    private val mainScreenView by lazy { ComposeView(this) }
    private val floatingButtonView by lazy { ComposeView(this) }
    private val screenSettingsInteractor: ScreenSettingsInteractor by lazy { Inject.instance() }
    private val scope = CoroutineScope(AndroidUiDispatcher.CurrentThread + SupervisorJob())
    private val mainScreenLayoutParams = WindowManager.LayoutParams()
    private val floatingButtonLayoutParams = WindowManager.LayoutParams()
    private val screenWidth by lazy { getScreenWidth(windowManager) }
    private val screenHeight by lazy { getScreenHeight(windowManager) }
    private val displayCutout by lazy { getDisplayCutoutSize(windowManager) }
    private val navigationBarHeight by lazy { getNavigationBarHeight(windowManager) }
    private val lifecycleOwner = MyLifecycleOwner()
    private var windowInFullScreen = true

    private var lastX: Int = 0
    private var lastY: Int = 0
    private var firstX: Int = 0
    private var firstY: Int = 0
    private var touchConsumedByMove = false

    init {
        scope.launch {
            screenSettingsInteractor.windowInFullScreen.collect { fullScreen ->
                windowInFullScreen = fullScreen
                if (fullScreen) {
                    floatingButtonLayoutParams.apply {
                        x = screenWidth - floatingButtonView.width - displayCutout
                        y = 0
                    }
                    mainScreenLayoutParams.apply {
                        flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    }
                } else {
                    floatingButtonLayoutParams.apply {
                        x = screenSettingsInteractor.buttonOffset.value?.x
                            ?: (screenWidth - floatingButtonView.width - displayCutout)
                        y = screenSettingsInteractor.buttonOffset.value?.y ?: 0
                    }
                    mainScreenLayoutParams.apply {
                        flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    }
                }

                updateMainWindow()
                updateFloatingButtonWindow()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        addComposeLifecycle()
        showMainOverlay()
        showFloatingButtonOverlay()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notification: Notification = NotificationCompat
            .Builder(this, CHANNEL_ID)
            .build()

        ServiceCompat.startForeground(
            /* service = */ this,
            /* id = */ 1,
            /* notification = */ notification,
            /* foregroundServiceType = */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            } else {
                0
            }
        )

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopService()

        exitProcess(0)
    }

    fun stopService() {
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)

        floatingButtonView.setViewTreeLifecycleOwner(null)
        floatingButtonView.setViewTreeViewModelStoreOwner(null)
        floatingButtonView.setViewTreeSavedStateRegistryOwner(null)
        windowManager?.removeView(floatingButtonView)
        floatingButtonView.disposeComposition()

        mainScreenView.setViewTreeLifecycleOwner(null)
        mainScreenView.setViewTreeViewModelStoreOwner(null)
        mainScreenView.setViewTreeSavedStateRegistryOwner(null)
        windowManager?.removeView(mainScreenView)
        mainScreenView.disposeComposition()

        scope.cancel()
        scope.coroutineContext.cancelChildren()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            @Suppress("DEPRECATION")
            stopForeground(true)
        }

        stopSelf()
    }

    private fun addComposeLifecycle() {
        // Trick The ComposeView into thinking we are tracking lifecycle
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        val viewModelStore = ViewModelStore()
        val recomposer = Recomposer(scope.coroutineContext)

        mainScreenView.setViewTreeLifecycleOwner(lifecycleOwner)
        mainScreenView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
        mainScreenView.setViewTreeViewModelStoreOwner(object : ViewModelStoreOwner {
            override val viewModelStore: ViewModelStore = viewModelStore
        })
        mainScreenView.compositionContext = recomposer

        floatingButtonView.setViewTreeLifecycleOwner(lifecycleOwner)
        floatingButtonView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
        floatingButtonView.setViewTreeViewModelStoreOwner(object : ViewModelStoreOwner {
            override val viewModelStore: ViewModelStore = viewModelStore
        })
        floatingButtonView.compositionContext = recomposer

        scope.launch {
            recomposer.runRecomposeAndApplyChanges()
        }
    }

    private fun showMainOverlay() {
        setMainLayoutParams()
        setMainContent()
        windowManager?.addView(mainScreenView, mainScreenLayoutParams)
        updateMainWindow()
    }

    private fun setMainLayoutParams() {
        mainScreenLayoutParams.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.TRANSLUCENT
        }
    }

    private fun setMainContent() {
        mainScreenView.setContent {
            AndroidApp(
                exitApp = { onTaskRemoved(null) }
            )
        }
    }

    private fun showFloatingButtonOverlay() {
        setFloatingButtonLayoutParams()
        setFloatingButtonContent()
        setFloatingButtonTouchListener()
        windowManager?.addView(floatingButtonView, floatingButtonLayoutParams)
        updateFloatingButtonWindow()
    }

    private fun setFloatingButtonLayoutParams() {
        floatingButtonLayoutParams.apply {
            gravity = Gravity.TOP or Gravity.START
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.TRANSLUCENT
            x = screenSettingsInteractor.buttonOffset.value?.x ?: 0
            y = screenSettingsInteractor.buttonOffset.value?.y ?: 0
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        }
    }

    private fun setFloatingButtonContent() {
        floatingButtonView.setContent {
            val windowInFullScreen by screenSettingsInteractor.windowInFullScreen.collectAsStateWithLifecycle()
            ChangeSizeButton(windowInFullScreen = windowInFullScreen)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setFloatingButtonTouchListener() {
        floatingButtonView.setOnTouchListener { view, event ->
            val totalDeltaX = lastX - firstX
            val totalDeltaY = lastY - firstY

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = event.rawX.toInt()
                    lastY = event.rawY.toInt()
                    firstX = lastX
                    firstY = lastY
                }

                MotionEvent.ACTION_UP -> {
                    view.performClick()
                    if (firstX == lastX && firstY == lastY && event.rawX.toInt() == lastX && event.rawY.toInt() == lastY) {
                        screenSettingsInteractor.setWindowInFullScreen(!windowInFullScreen)
                        updateMainWindow()
                    } else if (!windowInFullScreen) {
                        val buttonWidth = floatingButtonView.width
                        val maxWidthValue = screenWidth - buttonWidth - displayCutout
                        val finalX = floatingButtonLayoutParams.x.coerceIn(
                            minimumValue = 0,
                            maximumValue = maxWidthValue
                        )
                        val buttonHeight = floatingButtonView.height
                        val maxHeightValue = screenHeight - buttonHeight - navigationBarHeight
                        val finalY = floatingButtonLayoutParams.y.coerceIn(
                            minimumValue = 0,
                            maximumValue = maxHeightValue
                        )
                        floatingButtonLayoutParams.x = finalX
                        floatingButtonLayoutParams.y = finalY
                        screenSettingsInteractor.setButtonOffset(
                            ButtonOffset(
                                x = finalX,
                                y = finalY,
                            )
                        )
                        println("PARAMS SAVED: x = ${floatingButtonLayoutParams.x}, y = ${floatingButtonLayoutParams.y}")
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    if (!windowInFullScreen) {
                        changeRootPosition(event, totalDeltaX, totalDeltaY)
                    }
                }

                else -> {
                    touchConsumedByMove = false
                }
            }
            touchConsumedByMove
        }
    }

    private fun changeRootPosition(
        event: MotionEvent,
        totalDeltaX: Int,
        totalDeltaY: Int
    ) {
        val deltaX = event.rawX.toInt() - lastX
        val deltaY = event.rawY.toInt() - lastY
        lastX = event.rawX.toInt()
        lastY = event.rawY.toInt()
        if (abs(totalDeltaX) >= 5 || abs(totalDeltaY) >= 5) {
            if (event.pointerCount == 1) {
                floatingButtonLayoutParams.x += deltaX
                floatingButtonLayoutParams.y += deltaY
                touchConsumedByMove = true
                updateFloatingButtonWindow()
                println("MOVED: x = ${floatingButtonLayoutParams.x}, y = ${floatingButtonLayoutParams.y}")
            } else {
                touchConsumedByMove = false
            }
        } else {
            touchConsumedByMove = false
        }
    }

    private fun updateMainWindow() {
        windowManager?.apply {
            updateViewLayout(mainScreenView, mainScreenLayoutParams)
        }
    }

    private fun updateFloatingButtonWindow() {
        windowManager?.apply {
            updateViewLayout(floatingButtonView, floatingButtonLayoutParams)
        }
    }
}