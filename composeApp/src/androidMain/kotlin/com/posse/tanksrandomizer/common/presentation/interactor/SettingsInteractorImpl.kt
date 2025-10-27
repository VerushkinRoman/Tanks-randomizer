package com.posse.tanksrandomizer.common.presentation.interactor

import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation
import com.posse.tanksrandomizer.common.domain.models.ButtonOffset
import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SettingsInteractorImpl(
    private val repository: SettingsRepository,
    dispatchers: Dispatchers,
) : SettingsInteractor {
    private val scope: CoroutineScope = CoroutineScope(dispatchers.io + SupervisorJob())

    private val _windowInFullScreen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val windowInFullScreen: StateFlow<Boolean> = _windowInFullScreen.asStateFlow()

    private val _buttonLandscapeOffset: MutableStateFlow<ButtonOffset?> = MutableStateFlow(repository.getButtonLandscapeOffset())
    override val buttonLandscapeOffset: StateFlow<ButtonOffset?> = _buttonLandscapeOffset.asStateFlow()

    private val _buttonPortraitOffset: MutableStateFlow<ButtonOffset?> = MutableStateFlow(repository.getButtonPortraitOffset())
    override val buttonPortraitOffset: StateFlow<ButtonOffset?> = _buttonPortraitOffset.asStateFlow()

    private val _fullScreenMode: MutableStateFlow<Boolean> = MutableStateFlow(repository.getFullScreenMode())
    override val fullScreenModeEnabled: StateFlow<Boolean> = _fullScreenMode.asStateFlow()

    private val _floatingButtonOpacity: MutableStateFlow<Float> = MutableStateFlow(repository.getFloatingButtonOpacity())
    override val floatingButtonOpacity: StateFlow<Float> = _floatingButtonOpacity.asStateFlow()

    private val _floatingButtonSize: MutableStateFlow<Float> = MutableStateFlow(repository.getFloatingButtonSize())
    override val floatingButtonSize: StateFlow<Float> = _floatingButtonSize.asStateFlow()

    init {
        scope.launch {
            _floatingButtonSize
                .debounce(2000)
                .collect { size ->
                    repository.setFloatingButtonSize(size)
                }
        }
        scope.launch {
            _floatingButtonOpacity
                .debounce(2000)
                .collect { opacity ->
                    repository.setFloatingButtonOpacity(opacity)
                }
        }
    }

    override fun setWindowInFullScreen(fullScreen: Boolean) {
        _windowInFullScreen.value = fullScreen
//        repository.setWindowInFullScreen(fullScreen)
    }

    override fun setButtonLandscapeOffset(offset: ButtonOffset) {
        _buttonLandscapeOffset.value = offset
        repository.setButtonLandscapeOffset(offset)
    }

    override fun setButtonPortraitOffset(offset: ButtonOffset) {
        _buttonPortraitOffset.value = offset
        repository.setButtonPortraitOffset(offset)
    }

    override fun setFullScreenMode(fullScreen: Boolean) {
        _fullScreenMode.value = fullScreen
        repository.setFullScreenMode(fullScreen)
    }

    override fun setFloatingButtonOpacity(opacity: Float) {
        _floatingButtonOpacity.value = opacity
    }

    override fun setFloatingButtonSize(size: Float) {
        _floatingButtonSize.value = size
    }

    override fun setRotation(screenRotation: ScreenRotation) = repository.setRotation(screenRotation)
}
