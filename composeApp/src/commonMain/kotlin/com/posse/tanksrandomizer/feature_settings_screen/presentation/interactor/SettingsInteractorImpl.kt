package com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor

import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ButtonOffset
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation
import com.posse.tanksrandomizer.feature_settings_screen.domain.repository.SettingsRepository
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
    override val settingsRepository: SettingsRepository,
    dispatchers: Dispatchers,
) : SettingsInteractor {
    private val scope: CoroutineScope = CoroutineScope(dispatchers.io + SupervisorJob())

    private val _screenRotation: MutableStateFlow<ScreenRotation> = MutableStateFlow(settingsRepository.getRotation())
    override val screenRotation: StateFlow<ScreenRotation> = _screenRotation.asStateFlow()

    private val _windowInFullScreen: MutableStateFlow<Boolean> = MutableStateFlow(!settingsRepository.getAutoHideEnabled())
    override val windowInFullScreen: StateFlow<Boolean> = _windowInFullScreen.asStateFlow()

    private val _buttonLandscapeOffset: MutableStateFlow<ButtonOffset?> = MutableStateFlow(settingsRepository.getButtonLandscapeOffset())
    override val buttonLandscapeOffset: StateFlow<ButtonOffset?> = _buttonLandscapeOffset.asStateFlow()

    private val _buttonPortraitOffset: MutableStateFlow<ButtonOffset?> = MutableStateFlow(settingsRepository.getButtonPortraitOffset())
    override val buttonPortraitOffset: StateFlow<ButtonOffset?> = _buttonPortraitOffset.asStateFlow()

    private val _fullScreenMode: MutableStateFlow<Boolean> = MutableStateFlow(settingsRepository.getFullScreenMode())
    override val fullScreenModeEnabled: StateFlow<Boolean> = _fullScreenMode.asStateFlow()

    private val _floatingButtonOpacity: MutableStateFlow<Float> = MutableStateFlow(settingsRepository.getFloatingButtonOpacity())
    override val floatingButtonOpacity: StateFlow<Float> = _floatingButtonOpacity.asStateFlow()

    private val _floatingButtonSize: MutableStateFlow<Float> = MutableStateFlow(settingsRepository.getFloatingButtonSize())
    override val floatingButtonSize: StateFlow<Float> = _floatingButtonSize.asStateFlow()

    private val _desktopWindowSize: MutableStateFlow<Pair<Int, Int>?> = MutableStateFlow(settingsRepository.getDesktopWindowSize())

    init {
        scope.launch {
            _floatingButtonSize
                .debounce(2000)
                .collect { size ->
                    settingsRepository.setFloatingButtonSize(size)
                }
        }

        scope.launch {
            _floatingButtonOpacity
                .debounce(2000)
                .collect { opacity ->
                    settingsRepository.setFloatingButtonOpacity(opacity)
                }
        }

        scope.launch {
            _buttonPortraitOffset
                .debounce { 2000 }
                .collect { offset ->
                    offset?.let {
                        settingsRepository.setButtonPortraitOffset(offset)
                    }
                }
        }

        scope.launch {
            _buttonLandscapeOffset
                .debounce { 2000 }
                .collect { offset ->
                    offset?.let {
                        settingsRepository.setButtonLandscapeOffset(offset)
                    }
                }
        }

        scope.launch {
            _desktopWindowSize
                .debounce { 2000 }
                .collect { size ->
                    size?.let {
                        settingsRepository.setDesktopWindowSize(size)
                    }
                }
        }
    }

    override fun setWindowInFullScreen(fullScreen: Boolean) {
        _windowInFullScreen.value = fullScreen
    }

    override fun setButtonLandscapeOffset(offset: ButtonOffset) {
        _buttonLandscapeOffset.value = offset
    }

    override fun setButtonPortraitOffset(offset: ButtonOffset) {
        _buttonPortraitOffset.value = offset
    }

    override fun setFullScreenMode(fullScreen: Boolean) {
        _fullScreenMode.value = fullScreen
        settingsRepository.setFullScreenMode(fullScreen)
    }

    override fun setScreenRotation(rotation: ScreenRotation) {
        _screenRotation.value = rotation
        settingsRepository.setRotation(rotation)
    }

    override fun setFloatingButtonOpacity(opacity: Float) {
        _floatingButtonOpacity.value = opacity
    }

    override fun setFloatingButtonSize(size: Float) {
        _floatingButtonSize.value = size
    }

    override fun getDesktopWindowSize(): Pair<Int, Int>? = _desktopWindowSize.value
    override fun setDesktopWindowSize(size: Pair<Int, Int>) {
        _desktopWindowSize.value = size
    }
}
