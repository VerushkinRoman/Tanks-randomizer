package com.posse.tanksrandomizer.common.data

import android.content.SharedPreferences
import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
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

    override fun getButtonOffset(): ButtonOffset? = settings.decodeValueOrNull<ButtonOffset>("ButtonOffset")
    override fun setButtonOffset(buttonOffset: ButtonOffset) = settings.encodeValue("ButtonOffset", buttonOffset)
}