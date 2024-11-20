package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection

interface SettingsRepository {
    fun getAutorotate(): Boolean
    fun setAutorotate(autoRotate: Boolean)

    fun getRotation(): RotateDirection
    fun setRotation(rotateDirection: RotateDirection)

    fun getFullScreenMode(): Boolean
    fun setFullScreenMode(fullScreen: Boolean)
}