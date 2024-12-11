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
import androidx.compose.ui.platform.compositionContext
import androidx.core.content.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.presentation.interactor.ScreenSettingsInteractor
import com.posse.tanksrandomizer.feature_service.presentation.model.MyLifecycleOwner
import com.posse.tanksrandomizer.feature_service.presentation.model.TanksLayoutChangeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val screenSettingsInteractor: ScreenSettingsInteractor by lazy { Inject.instance() }
    private val layoutChangeListener: TanksLayoutChangeListener by lazy {
        TanksLayoutChangeListener {
            onLayoutChange()
            update()
        }
    }
    private lateinit var onLayoutChange: () -> Unit
    private lateinit var layoutParams: WindowManager.LayoutParams
    private lateinit var scope: CoroutineScope
    private var windowManager: WindowManager? = null

    constructor(
        context: Context,
        layoutParams: WindowManager.LayoutParams,
        scope: CoroutineScope,
        onLayoutChange: () -> Unit,
    ) : this(context) {
        this.layoutParams = layoutParams
        this.scope = scope
        this.onLayoutChange = onLayoutChange
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

    private fun init() {
        addOnLayoutChangeListener(layoutChangeListener)
        addComposeLifecycle()
        setLayoutParams()
        windowManager?.addView(this, layoutParams)

        scope.launch {
            screenSettingsInteractor.windowInFullScreen.collect { fullScreen ->
                layoutParams.apply {
                    flags = if (fullScreen) {
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    } else {
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
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

    fun update() {
        windowManager?.apply {
            updateViewLayout(this@MainScreenView, layoutParams)
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
        removeOnLayoutChangeListener(layoutChangeListener)
        setViewTreeLifecycleOwner(null)
        setViewTreeViewModelStoreOwner(null)
        setViewTreeSavedStateRegistryOwner(null)
        disposeComposition()
    }
}