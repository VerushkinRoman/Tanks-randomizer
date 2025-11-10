package com.posse.tanksrandomizer.feature_settings_screen.domain.repository.di

import com.posse.tanksrandomizer.feature_settings_screen.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.feature_settings_screen.domain.repository.SettingsRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val settingsRepositoryModule = DI.Module("SettingsRepositoryModule") {
    bind<SettingsRepository>() with singleton {
        SettingsRepositoryImpl(instance())
    }
}
