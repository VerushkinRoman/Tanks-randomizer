package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection
import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
import com.posse.tanksrandomizer.common.domain.model.ButtonSize

interface SettingsRepository {
    fun getAutorotate(): Boolean
    fun setAutorotate(autoRotate: Boolean)

    fun getRotation(): RotateDirection
    fun setRotation(rotateDirection: RotateDirection)

    fun getFullScreenMode(): Boolean
    fun setFullScreenMode(fullScreen: Boolean)

    fun getWindowInFullScreen(): Boolean
    fun setWindowInFullScreen(enabled: Boolean)

    fun getButtonLandscapeOffset(): ButtonOffset?
    fun setButtonLandscapeOffset(buttonOffset: ButtonOffset)

    fun getButtonPortraitOffset(): ButtonOffset?
    fun setButtonPortraitOffset(buttonOffset: ButtonOffset)

    fun getFloatingButtonOpacity(): Float
    fun setFloatingButtonOpacity(opacity: Float)

    fun getFloatingButtonSize(): ButtonSize
    fun setFloatingButtonSize(size: ButtonSize)
}