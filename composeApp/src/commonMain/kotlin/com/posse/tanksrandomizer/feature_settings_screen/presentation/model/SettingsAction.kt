package com.posse.tanksrandomizer.feature_settings_screen.presentation.model

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale

interface SettingsAction {
    data object GoToAppSettings : SettingsAction
    class UpdateLocale(val locale: AppLocale) : SettingsAction
}
