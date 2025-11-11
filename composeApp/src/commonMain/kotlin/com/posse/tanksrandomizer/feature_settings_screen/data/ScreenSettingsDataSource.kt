package com.posse.tanksrandomizer.feature_settings_screen.data

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ButtonOffset
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation

interface ScreenSettingsDataSource {
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

    fun getLocale(): AppLocale?
    fun setLocale(locale: AppLocale)
}
