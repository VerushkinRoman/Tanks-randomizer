package com.posse.tanksrandomizer.feature_settings_pane.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsState

class GetSettingsState(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): SettingsState {
        return SettingsState(
            screenRotation = repository.getRotation(),
            fullScreenMode = repository.getFullScreenMode(),
            buttonOpacity = repository.getFloatingButtonOpacity(),
            buttonSize = repository.getFloatingButtonSize(),
        )
    }
}
