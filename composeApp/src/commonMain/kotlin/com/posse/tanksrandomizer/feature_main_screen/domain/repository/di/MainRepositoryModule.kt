package com.posse.tanksrandomizer.feature_main_screen.domain.repository.di

import com.posse.tanksrandomizer.feature_main_screen.domain.repository.MainRepository
import com.posse.tanksrandomizer.feature_main_screen.domain.repository.MainRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val mainRepositoryModule = DI.Module("MainRepositoryModule") {
    bind<MainRepository>() with singleton {
        MainRepositoryImpl(instance())
    }
}