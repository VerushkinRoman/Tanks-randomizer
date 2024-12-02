package com.posse.tanksrandomizer.common.presentation.interactor

import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FullScreenModeInteractorImpl(
    private val settingsRepository: SettingsRepository,
) : FullScreenModeInteractor {
    private val _fullScreenMode: MutableStateFlow<Boolean> = MutableStateFlow(settingsRepository.getFullScreenMode())
    override val fullScreenModeEnabled: StateFlow<Boolean> = _fullScreenMode.asStateFlow()

    override fun setFullScreenMode(fullScreen: Boolean) {
        _fullScreenMode.value = fullScreen
        settingsRepository.setFullScreenMode(fullScreen)
    }
}