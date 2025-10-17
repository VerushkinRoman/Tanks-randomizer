package com.posse.tanksrandomizer.feature_settings_pane.presentation.model

import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation

sealed interface SettingsEvent {
    data object ClearAction : SettingsEvent
    data object OverlayButtonPressed : SettingsEvent
    class FullScreenModeChanged(val fullScreen: Boolean) : SettingsEvent
    class RotationChanged(val screenRotation: ScreenRotation) : SettingsEvent
    class SetButtonOpacity(val opacity: Float) : SettingsEvent
    class SetButtonSize(val size: Float) : SettingsEvent
}
