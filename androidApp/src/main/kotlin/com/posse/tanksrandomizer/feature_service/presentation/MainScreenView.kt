package com.posse.tanksrandomizer.feature_service.presentation

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.AttributeSet
import android.view.WindowManager
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
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.feature_service.presentation.model.MyLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainScreenView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val settingsInteractor: SettingsInteractor by lazy { Inject.instance() }
    private val scope = CoroutineScope(AndroidUiDispatcher.CurrentThread + SupervisorJob())

    private val layoutParams = WindowManager.LayoutParams()
    private var windowManager: WindowManager? = null

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

    private fun init() {
        addComposeLifecycle()
        setLayoutParams()
        windowManager?.addView(this, layoutParams)

        scope.launch {
            settingsInteractor.windowInFullScreen.collect { fullScreen ->
                layoutParams.apply {
                    flags = if (fullScreen) {
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                    } else {
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    }
                }
                update()
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

    private fun addComposeLifecycle() {
        // Trick The ComposeView into thinking we are tracking lifecycle

        lifecycleOwner.performRestore(null)
        setViewTreeLifecycleOwner(lifecycleOwner)
        setViewTreeSavedStateRegistryOwner(lifecycleOwner)

        setViewTreeViewModelStoreOwner(object : ViewModelStoreOwner {
            override val viewModelStore: ViewModelStore = this@MainScreenView.viewModelStore
        })

        val recomposer = Recomposer(scope.coroutineContext)
        compositionContext = recomposer

        scope.launch {
            recomposer.runRecomposeAndApplyChanges()
        }
    }

    private fun update() {
        windowManager?.apply {
            updateViewLayout(this@MainScreenView, layoutParams)
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
        setViewTreeLifecycleOwner(null)
        setViewTreeViewModelStoreOwner(null)
        setViewTreeSavedStateRegistryOwner(null)
        viewModelStore.clear()
        disposeComposition()
    }
}
