package com.posse.tanksrandomizer.common.domain.repository.di

import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.common.domain.repository.SettingsRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val settingsRepositoryModule = DI.Module("SettingsRepositoryModule") {
    bind<SettingsRepository>() with singleton {
        SettingsRepositoryImpl()
    }
}