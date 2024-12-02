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

    private val _buttonOffset: MutableStateFlow<ButtonOffset?> = MutableStateFlow(repository.getButtonOffset())
    override val buttonOffset: StateFlow<ButtonOffset?> = _buttonOffset.asStateFlow()

    override fun setWindowInFullScreen(fullScreen: Boolean) {
        _windowInFullScreen.value = fullScreen
        repository.setWindowInFullScreen(fullScreen)
    }

    override fun setButtonOffset(offset: ButtonOffset) {
        _buttonOffset.value = offset
        repository.setButtonOffset(offset)
    }
}