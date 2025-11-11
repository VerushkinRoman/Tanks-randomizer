package com.posse.tanksrandomizer.feature_settings_screen.presentation.use_cases

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsState

class GetSettingsState(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): SettingsState {
        return SettingsState(
            locale = repository.getLocale() ?: AppLocale.Ru,
            screenRotation = repository.getRotation(),
            fullScreenMode = repository.getFullScreenMode(),
            buttonOpacity = repository.getFloatingButtonOpacity(),
            buttonSize = repository.getFloatingButtonSize(),
        )
    }
}
