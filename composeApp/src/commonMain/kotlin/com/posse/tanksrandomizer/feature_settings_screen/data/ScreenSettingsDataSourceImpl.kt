package com.posse.tanksrandomizer.feature_settings_screen.data

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ButtonOffset
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class ScreenSettingsDataSourceImpl(
    private val settings: Settings
) : ScreenSettingsDataSource {
    override fun getButtonLandscapeOffset(): ButtonOffset? = settings.decodeValueOrNull<ButtonOffset>("ButtonLandscapeOffset")
    override fun setButtonLandscapeOffset(buttonOffset: ButtonOffset) = settings.encodeValue("ButtonLandscapeOffset", buttonOffset)

    override fun getButtonPortraitOffset(): ButtonOffset? = settings.decodeValueOrNull<ButtonOffset>("ButtonPortraitOffset")
    override fun setButtonPortraitOffset(buttonOffset: ButtonOffset) = settings.encodeValue("ButtonPortraitOffset", buttonOffset)

    override fun getRotation(): ScreenRotation? = settings.decodeValueOrNull<ScreenRotation>("ScreenRotation")
    override fun setRotation(rotation: ScreenRotation) = settings.encodeValue("ScreenRotation", rotation)

    override fun getFullScreenMode(): Boolean = settings.getBoolean("FullScreen", true)
    override fun setFullScreenMode(enabled: Boolean) = settings.putBoolean("FullScreen", enabled)

    override fun getFloatingButtonOpacity(): Float = settings.getFloat("ButtonOpacity", 1F)
    override fun setFloatingButtonOpacity(opacity: Float) = settings.putFloat("ButtonOpacity", opacity)

    override fun getFloatingButtonSize(): Float = settings.getFloat("FloatingButtonSize", 1F)
    override fun setFloatingButtonSize(size: Float) = settings.putFloat("FloatingButtonSize", size)

    override fun getLocale(): AppLocale? = settings.decodeValueOrNull<AppLocale>("AppLocale")
    override fun setLocale(locale: AppLocale) = settings.encodeValue("AppLocale", locale)

    override fun getDesktopWindowSize(): Pair<Int, Int>? = settings.decodeValueOrNull<Pair<Int, Int>>("DesktopWindowSize")
    override fun setDesktopWindowSize(size: Pair<Int, Int>) = settings.encodeValue("DesktopWindowSize", size)
}
