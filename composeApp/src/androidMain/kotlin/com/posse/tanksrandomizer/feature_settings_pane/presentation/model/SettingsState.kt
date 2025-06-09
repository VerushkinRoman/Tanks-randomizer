package com.posse.tanksrandomizer.feature_settings_pane.presentation.model

import com.posse.tanksrandomizer.common.domain.models.ButtonSize

data class SettingsState(
    val rotation: Rotation,
    val fullScreenMode: Boolean,
    val buttonOpacity: Float,
    val buttonSize: ButtonSize,
)