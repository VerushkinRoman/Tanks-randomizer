package com.posse.tanksrandomizer.feature_service.presentation

import android.annotation.SuppressLint
import android.content.Context
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
import com.posse.tanksrandomizer.common.presentation.interactor.ScreenSettingsInteractor
import com.posse.tanksrandomizer.feature_service.presentation.model.MyLifecycleOwner
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.appHeight
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.appWidth
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.portrait
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

class FloatingButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val screenSettingsInteractor: ScreenSettingsInteractor by lazy { Inject.instance() }
    private lateinit var layoutParams: WindowManager.LayoutParams
    private lateinit var scope: CoroutineScope
    private var windowManager: WindowManager? = null

    constructor(
        context: Context,
        layoutParams: WindowManager.LayoutParams,
        scope: CoroutineScope,
    ) : this(context) {
        this.layoutParams = layoutParams
        this.scope = scope
        windowManager = context.getSystemService<WindowManager>()

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
            screenSettingsInteractor.windowInFullScreen.collect { fullScreen ->
                windowInFullScreen = fullScreen
                updateLayoutParams(fullScreen)
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
            format = PixelFormat.TRANSLUCENT
            x = screenSettingsInteractor.buttonLandscapeOffset.value?.x ?: 0
            y = screenSettingsInteractor.buttonLandscapeOffset.value?.y ?: 0
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
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
                    lastX = event.rawX.toInt()
                    lastY = event.rawY.toInt()
                    firstX = lastX
                    firstY = lastY
                }

                MotionEvent.ACTION_UP -> {
                    view.performClick()
                    if (firstX == lastX && firstY == lastY && event.rawX.toInt() == lastX && event.rawY.toInt() == lastY) {
                        screenSettingsInteractor.setWindowInFullScreen(!windowInFullScreen)
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
                            screenSettingsInteractor.setButtonPortraitOffset(
                                ButtonOffset(
                                    x = finalX,
                                    y = finalY,
                                )
                            )
                        } else {
                            screenSettingsInteractor.setButtonLandscapeOffset(
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

    fun updateLayoutParams(fullScreen: Boolean = screenSettingsInteractor.windowInFullScreen.value) {
        if (fullScreen) {
            layoutParams.apply {
                x = context.appWidth - width
                y = 0
            }
        } else {
            layoutParams.apply {
                x = if (context.portrait) {
                    screenSettingsInteractor.buttonPortraitOffset.value?.x
                        ?: (context.appWidth - width)
                } else {
                    screenSettingsInteractor.buttonLandscapeOffset.value?.x
                        ?: (context.appWidth - width)
                }
                y = if (context.portrait) {
                    screenSettingsInteractor.buttonPortraitOffset.value?.y ?: 0
                } else {
                    screenSettingsInteractor.buttonLandscapeOffset.value?.y ?: 0
                }
            }
        }

        update()
    }

    fun update() {
        windowManager?.apply {
            updateViewLayout(this@FloatingButtonView, layoutParams)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        destroy()
    }

    fun destroy() {
        windowManager?.removeView(this)
        windowManager = null
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        setViewTreeLifecycleOwner(null)
        setViewTreeViewModelStoreOwner(null)
        setViewTreeSavedStateRegistryOwner(null)
        disposeComposition()
    }
}