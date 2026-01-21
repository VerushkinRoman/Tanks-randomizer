package com.posse.tanksrandomizer.feature_settings_screen.presentation.di

import com.posse.tanksrandomizer.feature_settings_screen.presentation.SettingsScreenViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val settingsScreenViewModelModule = DI.Module("SettingsScreenViewModelModule") {
    bindProvider<SettingsScreenViewModel> {
        SettingsScreenViewModel(
            settingsInteractor = instance(),
        )
    }
}
