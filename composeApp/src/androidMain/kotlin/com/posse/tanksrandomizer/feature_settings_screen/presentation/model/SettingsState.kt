package com.posse.tanksrandomizer.feature_settings_screen.presentation.model

import com.posse.tanksrandomizer.common.domain.model.ButtonSize

data class SettingsState(
    val rotation: Rotation,
    val fullScreenMode: Boolean,
    val buttonOpacity: Float,
    val buttonSize: ButtonSize,
)