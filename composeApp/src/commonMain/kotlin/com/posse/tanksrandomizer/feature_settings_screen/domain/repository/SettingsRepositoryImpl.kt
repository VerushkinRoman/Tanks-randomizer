package com.posse.tanksrandomizer.feature_settings_screen.domain.repository

import com.posse.tanksrandomizer.feature_settings_screen.data.ScreenSettingsDataSource
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ButtonOffset
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataSource: ScreenSettingsDataSource
) : SettingsRepository {
    override fun getRotation(): ScreenRotation = dataSource.getRotation() ?: ScreenRotation.Auto
    override fun setRotation(screenRotation: ScreenRotation) = dataSource.setRotation(screenRotation)

    override fun getFullScreenMode(): Boolean = dataSource.getFullScreenMode()
    override fun setFullScreenMode(fullScreen: Boolean) = dataSource.setFullScreenMode(fullScreen)

    override fun getButtonLandscapeOffset(): ButtonOffset? = dataSource.getButtonLandscapeOffset()
    override fun setButtonLandscapeOffset(buttonOffset: ButtonOffset) = dataSource.setButtonLandscapeOffset(buttonOffset)

    override fun getButtonPortraitOffset(): ButtonOffset? = dataSource.getButtonPortraitOffset()
    override fun setButtonPortraitOffset(buttonOffset: ButtonOffset) = dataSource.setButtonPortraitOffset(buttonOffset)

    override fun getFloatingButtonOpacity(): Float = dataSource.getFloatingButtonOpacity()
    override fun setFloatingButtonOpacity(opacity: Float) = dataSource.setFloatingButtonOpacity(opacity)

    override fun getFloatingButtonSize(): Float = dataSource.getFloatingButtonSize()
    override fun setFloatingButtonSize(size: Float) = dataSource.setFloatingButtonSize(size)

    override fun getLocale(): AppLocale? = dataSource.getLocale()
    override fun setLocale(locale: AppLocale) = dataSource.setLocale(locale)

    override fun getDesktopWindowSize(): Pair<Int, Int>? = dataSource.getDesktopWindowSize()
    override fun setDesktopWindowSize(size: Pair<Int, Int>) = dataSource.setDesktopWindowSize(size)

    override fun getMultiaccountEnabled(): Flow<Boolean> = dataSource.getMultiaccountEnabled()
    override fun setMultiaccountEnabled(enabled: Boolean) = dataSource.setMultiaccountEnabled(enabled)
}
