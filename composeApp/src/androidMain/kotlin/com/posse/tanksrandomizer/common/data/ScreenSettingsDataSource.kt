package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.domain.models.ButtonOffset
import com.posse.tanksrandomizer.common.domain.models.ButtonSize

interface ScreenSettingsDataSource {
    fun getWindowInFullScreen(): Boolean
    fun setWindowInFullScreen(enabled: Boolean)

    fun getButtonLandscapeOffset(): ButtonOffset?
    fun setButtonLandscapeOffset(buttonOffset: ButtonOffset)

    fun getButtonPortraitOffset(): ButtonOffset?
    fun setButtonPortraitOffset(buttonOffset: ButtonOffset)

    fun getAutorotate(): Boolean
    fun setAutorotate(autoRotate: Boolean)

    fun getRotation(): String?
    fun setRotation(rotation: String)

    fun getFullScreenMode(): Boolean
    fun setFullScreenMode(enabled: Boolean)

    fun getFloatingButtonOpacity(): Float
    fun setFloatingButtonOpacity(opacity: Float)

    fun getFloatingButtonSize(): ButtonSize
    fun setFloatingButtonSize(size: ButtonSize)
}