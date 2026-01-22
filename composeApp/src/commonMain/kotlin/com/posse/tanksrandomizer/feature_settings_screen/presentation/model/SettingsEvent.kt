package com.posse.tanksrandomizer.feature_settings_screen.presentation.model

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation

sealed interface SettingsEvent {
    data object ClearAction : SettingsEvent
    data object OverlayButtonPressed : SettingsEvent
    class FullScreenModeChanged(val fullScreen: Boolean) : SettingsEvent
    class RotationChanged(val screenRotation: ScreenRotation) : SettingsEvent
    class SetButtonOpacity(val opacity: Float) : SettingsEvent
    class SetButtonSize(val size: Float) : SettingsEvent
    class ChangeLocale(val locale: AppLocale) : SettingsEvent
    class MultiaccountEnabled(val enabled: Boolean) : SettingsEvent
    class AutoHideChanged(val enabled: Boolean) : SettingsEvent
    data object MultiaccountClicked : SettingsEvent
}
