package com.posse.tanksrandomizer.feature_settings_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsState
import kotlinx.coroutines.withContext

class SaveSettingsState(
    private val repository: SettingsRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(state: SettingsState) = withContext(dispatchers.io) {
        repository.setAutorotate(state.rotation.autoRotateEnabled)
        repository.setRotation(state.rotation.rotateDirection)
        repository.setFullScreenMode(state.fullScreenMode)
    }
}