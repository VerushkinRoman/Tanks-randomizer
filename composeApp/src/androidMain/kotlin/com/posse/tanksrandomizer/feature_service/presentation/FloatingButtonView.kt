package com.posse.tanksrandomizer.feature_service.presentation

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.PixelFormat
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.compositionContext
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ButtonOffset
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.feature_service.presentation.model.MyLifecycleOwner
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.appHeight
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.appWidth
import com.posse.tanksrandomizer.feature_service.utils.WindowUtils.portrait
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs

class FloatingButtonView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private data class Position(val x: Int, val y: Int)

    private val settingsInteractor: SettingsInteractor by lazy { Inject.instance() }
    private val scope = CoroutineScope(AndroidUiDispatcher.CurrentThread + SupervisorJob())

    private val layoutParams = WindowManager.LayoutParams()
    private var windowManager: WindowManager? = null
    private var touchedTime: Long = 0
    private val animator = ValueAnimator.ofFloat(0f, 1f)
    private var sizeInitialized: Boolean = false

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
    private val lifecycleOwner = MyLifecycleOwner(
        (context as? LifecycleOwner)
            ?: throw IllegalArgumentException("Context must implement LifecycleOwner")
    )
    private val viewModelStore = ViewModelStore()

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

    private val windowInFullScreen = settingsInteractor.windowInFullScreen

    private fun init() {
        addComposeLifecycle()
        setLayoutParams()
        setTouchListener()
        configureAnimator()
        windowManager?.addView(this, layoutParams)
        setUpdates()
    }

    private fun setUpdates() {
        scope.launch {
            windowInFullScreen.collect {
                if (sizeInitialized) {
                    updateLayoutParams()
                }
            }
        }

        scope.launch {
            while (windowManager != null && isActive) {
                delay(1000)

                if (!windowInFullScreen.value || !sizeInitialized) continue

                val targetPosition = getTargetPosition()
                if (targetPosition.x != layoutParams.x || targetPosition.y != layoutParams.y) {
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
        setViewTreeLifecycleOwner(lifecycleOwner)
        setViewTreeSavedStateRegistryOwner(lifecycleOwner)

        setViewTreeViewModelStoreOwner(object : ViewModelStoreOwner {
            override val viewModelStore: ViewModelStore = this@FloatingButtonView.viewModelStore
        })

        val recomposer = Recomposer(scope.coroutineContext)
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
                    when {
                        animator.isRunning -> Unit

                        isNoMovement(event) || isTouchedFast() -> {
                            view.performClick()
                            settingsInteractor.setWindowInFullScreen(!windowInFullScreen.value)
                        }

                        !windowInFullScreen.value -> {
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
                }

                MotionEvent.ACTION_MOVE -> {
                    if (!windowInFullScreen.value) {
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

    private fun configureAnimator() {
        animator.duration = ANIMATION_TIME
        animator.interpolator = AccelerateDecelerateInterpolator()
    }

    private fun updateLayoutParams() {
        if (animator.isRunning) return

        val startPosition = getStartPosition()
        val startX: Int = startPosition.x
        val startY: Int = startPosition.y

        val endPosition = getTargetPosition()
        val endX: Int = endPosition.x
        val endY: Int = endPosition.y

        if (startPosition != endPosition) {
            animateMovement(startX, startY, endX, endY)
        } else {
            layoutParams.apply {
                x = endX
                y = endY
            }

            update()
        }
    }

    private fun animateMovement(startX: Int, startY: Int, endX: Int, endY: Int) {
        animator.addUpdateListener { animation ->
            val fraction = animation.animatedValue as Float

            val currentX = startX + (endX - startX) * fraction
            val currentY = startY + (endY - startY) * fraction

            layoutParams.apply {
                x = currentX.toInt()
                y = currentY.toInt()
            }
            update()
        }

        animator.start()
    }

    private fun getTargetPosition(): Position {
        val x: Int
        val y: Int
        if (windowInFullScreen.value) {
            x = context.appWidth - measuredWidth
            y = 0
        } else {
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
        return Position(x, y)
    }

    private fun getStartPosition(): Position {
        val x: Int
        val y: Int
        if (windowInFullScreen.value) {
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
        } else {
            x = context.appWidth - measuredWidth
            y = 0
        }
        return Position(x, y)
    }

    private fun isNoMovement(event: MotionEvent): Boolean {
        return firstX == lastX
                && firstY == lastY
                && event.rawX.toInt() == lastX
                && event.rawY.toInt() == lastY
    }

    private fun isTouchedFast(): Boolean {
        return System.currentTimeMillis() - touchedTime < 200
    }

    private fun setTargetPosition() {
        val position = getTargetPosition()
        layoutParams.apply {
            x = position.x
            y = position.y
        }
        update()
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

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        setTargetPosition()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setTargetPosition()
        sizeInitialized = true
    }

    fun destroy() {
        scope.cancel()
        windowManager?.removeView(this)
        windowManager = null
        setViewTreeLifecycleOwner(null)
        setViewTreeViewModelStoreOwner(null)
        setViewTreeSavedStateRegistryOwner(null)
        viewModelStore.clear()
        disposeComposition()
    }

    companion object {
        private const val ANIMATION_TIME = 500L
    }
}
