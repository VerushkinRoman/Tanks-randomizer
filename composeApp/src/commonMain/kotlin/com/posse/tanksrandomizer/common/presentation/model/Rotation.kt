package com.posse.tanksrandomizer.common.presentation.model

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection

data class Rotation(
    val autoRotateEnabled: Boolean = true,
    val rotateDirection: RotateDirection = RotateDirection.Portrait
)
