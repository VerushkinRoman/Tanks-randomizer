package com.posse.tanksrandomizer.common.presentation.interactor

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection
import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
import com.posse.tanksrandomizer.common.domain.model.ButtonSize
import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsInteractorImpl(
    private val repository: SettingsRepository
) : SettingsInteractor {
    private val _windowInFullScreen: MutableStateFlow<Boolean> = MutableStateFlow(repository.getWindowInFullScreen())
    override val windowInFullScreen: StateFlow<Boolean> = _windowInFullScreen.asStateFlow()

    private val _buttonLandscapeOffset: MutableStateFlow<ButtonOffset?> = MutableStateFlow(repository.getButtonLandscapeOffset())
    override val buttonLandscapeOffset: StateFlow<ButtonOffset?> = _buttonLandscapeOffset.asStateFlow()

    private val _buttonPortraitOffset: MutableStateFlow<ButtonOffset?> = MutableStateFlow(repository.getButtonPortraitOffset())
    override val buttonPortraitOffset: StateFlow<ButtonOffset?> = _buttonPortraitOffset.asStateFlow()

    private val _fullScreenMode: MutableStateFlow<Boolean> = MutableStateFlow(repository.getFullScreenMode())
    override val fullScreenModeEnabled: StateFlow<Boolean> = _fullScreenMode.asStateFlow()

    private val _floatingButtonOpacity: MutableStateFlow<Float> = MutableStateFlow(repository.getFloatingButtonOpacity())
    override val floatingButtonOpacity: StateFlow<Float> = _floatingButtonOpacity.asStateFlow()

    private val _floatingButtonSize: MutableStateFlow<ButtonSize> = MutableStateFlow(repository.getFloatingButtonSize())
    override val floatingButtonSize: StateFlow<ButtonSize> = _floatingButtonSize.asStateFlow()

    override fun setWindowInFullScreen(fullScreen: Boolean) {
        _windowInFullScreen.value = fullScreen
        repository.setWindowInFullScreen(fullScreen)
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
        repository.setFloatingButtonOpacity(opacity)
    }

    override fun setFloatingButtonSize(size: ButtonSize) {
        _floatingButtonSize.value = size
        repository.setFloatingButtonSize(size)
    }

    override fun setAutorotate(autoRotate: Boolean) = repository.setAutorotate(autoRotate)
    override fun setRotation(rotateDirection: RotateDirection) = repository.setRotation(rotateDirection)
}