package com.posse.tanksrandomizer.feature_settings_screen.data.di

import com.posse.tanksrandomizer.feature_settings_screen.data.ScreenSettingsDataSource
import com.posse.tanksrandomizer.feature_settings_screen.data.ScreenSettingsDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val settingsDataSourceModule = DI.Module("SettingsDataSourceModule") {
    bind<ScreenSettingsDataSource>() with singleton {
        ScreenSettingsDataSourceImpl(
            settings = instance(),
            observableSettings = instance()
        )
    }
}
