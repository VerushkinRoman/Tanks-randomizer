package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation
import com.posse.tanksrandomizer.common.domain.models.ButtonOffset

interface SettingsRepository {
    fun getRotation(): ScreenRotation
    fun setRotation(screenRotation: ScreenRotation)

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

    fun getFloatingButtonSize(): Float
    fun setFloatingButtonSize(size: Float)
}
