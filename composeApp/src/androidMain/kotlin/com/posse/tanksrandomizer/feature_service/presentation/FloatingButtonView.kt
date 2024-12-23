package com.posse.tanksrandomizer.feature_service.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.compositionContext
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
import com.posse.tanksrandomizer.common.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.feature_service.presentation.model.MyLifecycleOwner
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.appHeight
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.appWidth
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.portrait
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class FloatingButtonView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val settingsInteractor: SettingsInteractor by lazy { Inject.instance() }
    private val scope = CoroutineScope(AndroidUiDispatcher.CurrentThread + SupervisorJob())

    private val layoutParams = WindowManager.LayoutParams()
    private var windowManager: WindowManager? = null
    private var touchedTime: Long = 0
    private var orientation: Int? = resources.configuration.orientation

    constructor(
        context: Context,
    ) : this(
        context = context,
        attrs = null,
        defStyleAttr = 0,
    ) {
        this.windowManager = context.getSystemService<WindowManager>()
        init()
    }

    private val content = mutableStateOf<(@Composable () -> Unit)?>(null)
    private val lifecycleOwner = MyLifecycleOwner()

    @Suppress("RedundantVisibilityModifier")
    protected override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    override fun getAccessibilityClassName(): CharSequence {
        return javaClass.name
    }

    private var lastX: Int = 0
    private var lastY: Int = 0
    private var firstX: Int = 0
    private var firstY: Int = 0
    private var touchConsumedByMove = false

    private var windowInFullScreen = true

    private fun init() {
        addComposeLifecycle()
        setLayoutParams()
        setTouchListener()
        windowManager?.addView(this, layoutParams)

        scope.launch {
            settingsInteractor.windowInFullScreen.collect { fullScreen ->
                windowInFullScreen = fullScreen

                if (touchedTime == 0L && fullScreen && context.portrait) {
                    settingsInteractor.setWindowInFullScreen(false)
                }

                if (fullScreen) {
                    skipButtonGlitch()
                } else {
                    updateLayoutParams()
                }
            }
        }
    }

    @Composable
    override fun Content() {
        content.value?.invoke()
    }

    fun setContent(content: @Composable () -> Unit) {
        shouldCreateCompositionOnAttachedToWindow = true
        this.content.value = content
        if (isAttachedToWindow) {
            createComposition()
        }
    }

    private fun setLayoutParams() {
        layoutParams.apply {
            gravity = Gravity.TOP or Gravity.START
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.TRANSPARENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        }
    }

    private fun addComposeLifecycle() {
        // Trick The ComposeView into thinking we are tracking lifecycle
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        val viewModelStore = ViewModelStore()
        val recomposer = Recomposer(scope.coroutineContext)

        setViewTreeLifecycleOwner(lifecycleOwner)
        setViewTreeSavedStateRegistryOwner(lifecycleOwner)
        setViewTreeViewModelStoreOwner(object : ViewModelStoreOwner {
            override val viewModelStore: ViewModelStore = viewModelStore
        })
        compositionContext = recomposer

        scope.launch {
            recomposer.runRecomposeAndApplyChanges()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener() {
        setOnTouchListener { view, event ->
            val totalDeltaX = lastX - firstX
            val totalDeltaY = lastY - firstY

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    touchedTime = System.currentTimeMillis()
                    lastX = event.rawX.toInt()
                    lastY = event.rawY.toInt()
                    firstX = lastX
                    firstY = lastY
                }

                MotionEvent.ACTION_UP -> {
                    view.performClick()
                    if (
                        (firstX == lastX && firstY == lastY && event.rawX.toInt() == lastX && event.rawY.toInt() == lastY)
                        || System.currentTimeMillis() - touchedTime < 200
                    ) {
                        settingsInteractor.setWindowInFullScreen(!windowInFullScreen)
                    } else if (!windowInFullScreen) {
                        val finalX = layoutParams.x.coerceIn(
                            minimumValue = 0,
                            maximumValue = context.appWidth - width
                        )
                        val finalY = layoutParams.y.coerceIn(
                            minimumValue = 0,
                            maximumValue = context.appHeight - height
                        )
                        layoutParams.x = finalX
                        layoutParams.y = finalY
                        if (context.portrait) {
                            settingsInteractor.setButtonPortraitOffset(
                                ButtonOffset(
                                    x = finalX,
                                    y = finalY,
                                )
                            )
                        } else {
                            settingsInteractor.setButtonLandscapeOffset(
                                ButtonOffset(
                                    x = finalX,
                                    y = finalY,
                                )
                            )
                        }
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
                layoutParams.x += deltaX
                layoutParams.y += deltaY
                touchConsumedByMove = true
                update()
            } else {
                touchConsumedByMove = false
            }
        } else {
            touchConsumedByMove = false
        }
    }

    private fun updateLayoutParams(fullScreen: Boolean = windowInFullScreen) {
        if (fullScreen) {
            layoutParams.apply {
                x = context.appWidth - measuredWidth
                y = 0
            }
        } else {
            layoutParams.apply {
                x = if (context.portrait) {
                    settingsInteractor.buttonPortraitOffset.value?.x
                        ?: (context.appWidth / 2 - measuredWidth / 2)
                } else {
                    settingsInteractor.buttonLandscapeOffset.value?.x
                        ?: (context.appWidth - measuredWidth)
                }
                y = if (context.portrait) {
                    settingsInteractor.buttonPortraitOffset.value?.y
                        ?: (context.appHeight - measuredHeight)
                } else {
                    settingsInteractor.buttonLandscapeOffset.value?.y
                        ?: 0
                }
            }
        }

        update()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (orientation != newConfig?.orientation) {
            orientation = newConfig?.orientation
            skipButtonGlitch()
        }
    }

    private fun skipButtonGlitch() {
        scope.launch {
            val prevHeight = layoutParams.height
            val prevWidth = layoutParams.width
            layoutParams.apply {
                width = 0
                height = 0
            }
            updateLayoutParams()
            delay(100)
            layoutParams.apply {
                width = prevWidth
                height = prevHeight
            }
            updateLayoutParams()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (oldw == 0 && oldh == 0 && touchedTime == 0L) {
            changeFistTimeAppMinimize(w, h)
        }
    }

    private fun changeFistTimeAppMinimize(w: Int, h: Int) {
        val portraitX = settingsInteractor.buttonPortraitOffset.value?.x
        val portraitY = settingsInteractor.buttonPortraitOffset.value?.y
        val landscapeX = settingsInteractor.buttonLandscapeOffset.value?.x
        val landscapeY = settingsInteractor.buttonLandscapeOffset.value?.y
        if (context.portrait) {
            val notFirstLaunch =
                portraitX != null || portraitY != null || landscapeX != null || landscapeY != null
            settingsInteractor.setButtonPortraitOffset(
                ButtonOffset(
                    x = if (notFirstLaunch) portraitX ?: (context.appWidth - w)
                    else context.appWidth / 2 - w / 2,
                    y = if (notFirstLaunch) portraitY ?: 0
                    else context.appHeight - h,
                )
            )
            updateLayoutParams(false)
        } else {
            settingsInteractor.setButtonLandscapeOffset(
                ButtonOffset(
                    x = landscapeX ?: (context.appWidth - w),
                    y = landscapeY ?: 0,
                )
            )
            updateLayoutParams()
        }
    }

    private fun update() {
        windowManager?.apply {
            updateViewLayout(this@FloatingButtonView, layoutParams)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        destroy()
    }

    fun destroy() {
        scope.cancel()
        windowManager?.removeView(this)
        windowManager = null
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        setViewTreeLifecycleOwner(null)
        setViewTreeViewModelStoreOwner(null)
        setViewTreeSavedStateRegistryOwner(null)
        disposeComposition()
    }
}