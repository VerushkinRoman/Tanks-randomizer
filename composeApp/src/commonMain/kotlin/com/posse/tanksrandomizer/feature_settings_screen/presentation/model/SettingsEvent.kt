package com.posse.tanksrandomizer.feature_settings_screen.presentation.model

sealed interface SettingsEvent {
    data object ClearAction : SettingsEvent
    data object FullScreenModePressed : SettingsEvent
    data object RotateSwitchChecked : SettingsEvent
    data object LandscapeRotatePressed : SettingsEvent
    data object PortraitRotatePressed : SettingsEvent
    data object AppSettingsPressed : SettingsEvent
}