package com.posse.tanksrandomizer.feature_settings_screen.data

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ButtonOffset
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getBooleanFlow
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class ScreenSettingsDataSourceImpl(
    private val settings: Settings,
    private val observableSettings: ObservableSettings,
) : ScreenSettingsDataSource {
    override fun getButtonLandscapeOffset(): ButtonOffset? = settings.decodeValueOrNull<ButtonOffset>(BUTTON_LANDSCAPE_OFFSET_KEY)
    override fun setButtonLandscapeOffset(buttonOffset: ButtonOffset) = settings.encodeValue(BUTTON_LANDSCAPE_OFFSET_KEY, buttonOffset)

    override fun getButtonPortraitOffset(): ButtonOffset? = settings.decodeValueOrNull<ButtonOffset>(BUTTON_PORTRAIT_OFFSET_KEY)
    override fun setButtonPortraitOffset(buttonOffset: ButtonOffset) = settings.encodeValue(BUTTON_PORTRAIT_OFFSET_KEY, buttonOffset)

    override fun getRotation(): ScreenRotation? = settings.decodeValueOrNull<ScreenRotation>(SCREEN_ROTATION_KEY)
    override fun setRotation(rotation: ScreenRotation) = settings.encodeValue(SCREEN_ROTATION_KEY, rotation)

    override fun getFullScreenMode(): Boolean = settings.getBoolean(FULL_SCREEN_KEY, true)
    override fun setFullScreenMode(enabled: Boolean) = settings.putBoolean(FULL_SCREEN_KEY, enabled)

    override fun getFloatingButtonOpacity(): Float = settings.getFloat(BUTTON_OPACITY_KEY, 1F)
    override fun setFloatingButtonOpacity(opacity: Float) = settings.putFloat(BUTTON_OPACITY_KEY, opacity)

    override fun getFloatingButtonSize(): Float = settings.getFloat(FLOATING_BUTTON_SIZE_KEY, 1F)
    override fun setFloatingButtonSize(size: Float) = settings.putFloat(FLOATING_BUTTON_SIZE_KEY, size)

    override fun getLocale(): AppLocale? = settings.decodeValueOrNull<AppLocale>(APP_LOCALE_KEY)
    override fun setLocale(locale: AppLocale) = settings.encodeValue(APP_LOCALE_KEY, locale)

    override fun getDesktopWindowSize(): Pair<Int, Int>? = settings.decodeValueOrNull<Pair<Int, Int>>(DESKTOP_WINDOW_SIZE_KEY)
    override fun setDesktopWindowSize(size: Pair<Int, Int>) = settings.encodeValue(DESKTOP_WINDOW_SIZE_KEY, size)

    override fun getMultiaccountEnabled(): Flow<Boolean> = observableSettings.getBooleanFlow(MULTIACCOUNT_KEY, true)
    override fun setMultiaccountEnabled(enabled: Boolean) = observableSettings.putBoolean(MULTIACCOUNT_KEY, enabled)

    override fun getAutoHideEnabled(): Boolean = settings.getBoolean(AUTOHIDE_KEY, false)
    override fun setAutohideEnabled(enabled: Boolean) = settings.putBoolean(AUTOHIDE_KEY, enabled)

    companion object {
        private const val BUTTON_LANDSCAPE_OFFSET_KEY = "ButtonLandscapeOffset"
        private const val BUTTON_PORTRAIT_OFFSET_KEY = "ButtonPortraitOffset"
        private const val SCREEN_ROTATION_KEY = "ScreenRotation"
        private const val FULL_SCREEN_KEY = "FullScreen"
        private const val BUTTON_OPACITY_KEY = "ButtonOpacity"
        private const val FLOATING_BUTTON_SIZE_KEY = "FloatingButtonSize"
        private const val APP_LOCALE_KEY = "AppLocale"
        private const val DESKTOP_WINDOW_SIZE_KEY = "DesktopWindowSize"
        private const val MULTIACCOUNT_KEY = "Multiaccount"
        private const val AUTOHIDE_KEY = "AutoHide"
    }
}
