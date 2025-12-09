package com.posse.tanksrandomizer.feature_settings_screen.domain.repository

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ButtonOffset
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation
import kotlinx.coroutines.flow.Flow

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

    fun getLocale(): AppLocale?
    fun setLocale(locale: AppLocale)

    fun getDesktopWindowSize(): Pair<Int, Int>?
    fun setDesktopWindowSize(size: Pair<Int, Int>)

    fun getMultiaccountEnabled(): Flow<Boolean>
    fun setMultiaccountEnabled(enabled: Boolean)
}
