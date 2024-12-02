package com.posse.tanksrandomizer.common.domain.repository.di

import com.posse.tanksrandomizer.common.domain.repository.ScreenSettingsRepository
import com.posse.tanksrandomizer.common.domain.repository.ScreenSettingsRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val screenSettingsRepositoryModule = DI.Module("ScreenSettingsRepositoryModule") {
    bind<ScreenSettingsRepository>() with singleton {
        ScreenSettingsRepositoryImpl(instance())
    }
}