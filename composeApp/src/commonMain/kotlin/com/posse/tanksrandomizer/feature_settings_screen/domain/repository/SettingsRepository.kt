package com.posse.tanksrandomizer.feature_settings_screen.domain.repository

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ButtonOffset
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation

interface SettingsRepository {
    fun getRotation(): ScreenRotation
    fun setRotation(screenRotation: ScreenRotation)

    fun getFullScreenMode(): Boolean
    fun setFullScreenMode(fullScreen: Boolean)

    fun getButtonLandscapeOffset(): ButtonOffset?
    fun setButtonLandscapeOffset(buttonOffset: ButtonOffset)

    fun getButtonPortraitOffset(): ButtonOffset?
    fun setButtonPortraitOffset(buttonOffset: ButtonOffset)

    fun getFloatingButtonOpacity(): Float
    fun setFloatingButtonOpacity(opacity: Float)

    fun getFloatingButtonSize(): Float
    fun setFloatingButtonSize(size: Float)
}
