package com.posse.tanksrandomizer.feature_settings_pane.presentation.model

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection

data class Rotation(
    val autoRotateEnabled: Boolean = true,
    val rotateDirection: RotateDirection = RotateDirection.Portrait
)