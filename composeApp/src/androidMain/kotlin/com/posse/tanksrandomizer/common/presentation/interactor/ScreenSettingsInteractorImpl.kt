package com.posse.tanksrandomizer.common.presentation.interactor

import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
import com.posse.tanksrandomizer.common.domain.repository.ScreenSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScreenSettingsInteractorImpl(
    private val repository: ScreenSettingsRepository
): ScreenSettingsInteractor {
    private val _windowInFullScreen: MutableStateFlow<Boolean> = MutableStateFlow(repository.getWindowInFullScreen())
    override val windowInFullScreen: StateFlow<Boolean> = _windowInFullScreen.asStateFlow()

    private val _buttonLandscapeOffset: MutableStateFlow<ButtonOffset?> = MutableStateFlow(repository.getButtonLandscapeOffset())
    override val buttonLandscapeOffset: StateFlow<ButtonOffset?> = _buttonLandscapeOffset.asStateFlow()

    private val _buttonPortraitOffset: MutableStateFlow<ButtonOffset?> = MutableStateFlow(repository.getButtonPortraitOffset())
    override val buttonPortraitOffset: StateFlow<ButtonOffset?> = _buttonPortraitOffset.asStateFlow()

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
}