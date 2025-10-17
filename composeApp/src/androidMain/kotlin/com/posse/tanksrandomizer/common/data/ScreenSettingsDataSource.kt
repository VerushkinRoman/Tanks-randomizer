package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation
import com.posse.tanksrandomizer.common.domain.models.ButtonOffset

interface ScreenSettingsDataSource {
    fun getWindowInFullScreen(): Boolean
    fun setWindowInFullScreen(enabled: Boolean)

    fun getButtonLandscapeOffset(): ButtonOffset?
    fun setButtonLandscapeOffset(buttonOffset: ButtonOffset)

    fun getButtonPortraitOffset(): ButtonOffset?
    fun setButtonPortraitOffset(buttonOffset: ButtonOffset)

    fun getRotation(): ScreenRotation?
    fun setRotation(rotation: ScreenRotation)

    fun getFullScreenMode(): Boolean
    fun setFullScreenMode(enabled: Boolean)

    fun getFloatingButtonOpacity(): Float
    fun setFloatingButtonOpacity(opacity: Float)

    fun getFloatingButtonSize(): Float
    fun setFloatingButtonSize(size: Float)
}
