package com.posse.tanksrandomizer.feature_settings_screen.presentation.model

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation

data class SettingsState(
    val locale: AppLocale,
    val screenRotation: ScreenRotation,
    val fullScreenMode: Boolean,
    val buttonOpacity: Float,
    val buttonSize: Float,
    val multiaccountEnabled: Boolean,
)
