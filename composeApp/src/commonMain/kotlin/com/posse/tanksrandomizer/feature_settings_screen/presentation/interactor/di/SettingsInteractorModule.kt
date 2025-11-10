package com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.di

import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractorImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val settingsInteractorModule = DI.Module("SettingsInteractorModule") {
    bind<SettingsInteractor>() with singleton {
        SettingsInteractorImpl(
            repository = instance(),
            dispatchers = instance(),
        )
    }
}
