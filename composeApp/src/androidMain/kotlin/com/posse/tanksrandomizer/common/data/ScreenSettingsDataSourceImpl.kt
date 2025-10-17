package com.posse.tanksrandomizer.common.data

import android.content.SharedPreferences
import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation
import com.posse.tanksrandomizer.common.domain.models.ButtonOffset
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class ScreenSettingsDataSourceImpl(
    sharedPreferences: SharedPreferences
) : ScreenSettingsDataSource {
    private val settings: SharedPreferencesSettings = SharedPreferencesSettings(sharedPreferences)

    override fun getWindowInFullScreen(): Boolean = settings.getBoolean("WindowInFullScreen", true)
    override fun setWindowInFullScreen(enabled: Boolean) = settings.putBoolean("WindowInFullScreen", enabled)

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
}
