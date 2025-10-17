package com.posse.tanksrandomizer.feature_settings_pane.presentation.model

import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation

data class SettingsState(
    val screenRotation: ScreenRotation,
    val fullScreenMode: Boolean,
    val buttonOpacity: Float,
    val buttonSize: Float,
)
