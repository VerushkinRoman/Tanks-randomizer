package com.posse.tanksrandomizer.feature_settings_screen.presentation.model

import com.posse.tanksrandomizer.common.presentation.model.Rotation

data class SettingsState(
    val rotation: Rotation,
    val fullScreenMode: Boolean,
)