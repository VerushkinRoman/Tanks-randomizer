package com.posse.tanksrandomizer.feature_settings_screen.presentation.model

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection

data class Rotation(
    val autoRotateEnabled: Boolean = true,
    val rotateDirection: RotateDirection = RotateDirection.Portrait
)