package com.posse.tanksrandomizer.feature_settings_screen.presentation.model

interface SettingsAction {
    data object NavigateBack : SettingsAction
    data object GoToAppSettings : SettingsAction
}