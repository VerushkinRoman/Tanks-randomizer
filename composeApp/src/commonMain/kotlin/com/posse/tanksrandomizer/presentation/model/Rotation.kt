package com.posse.tanksrandomizer.presentation.model

import com.posse.tanksrandomizer.utils.RotateDirection

data class Rotation(
    val autoRotateEnabled: Boolean = true,
    val rotateDirection: RotateDirection = RotateDirection.Portrait
)
