package com.posse.tanksrandomizer.feature_settings_pane.presentation.model

import com.posse.tanksrandomizer.common.domain.models.ButtonSize

sealed interface SettingsEvent {
    data object ClearAction : SettingsEvent
    data object FullScreenModePressed : SettingsEvent
    data object RotateSwitchChecked : SettingsEvent
    data object LandscapeRotatePressed : SettingsEvent
    data object PortraitRotatePressed : SettingsEvent
    data object AppSettingsPressed : SettingsEvent
    class SetButtonOpacity(val opacity: Float) : SettingsEvent
    class SetButtonSize(val size: ButtonSize) : SettingsEvent
}