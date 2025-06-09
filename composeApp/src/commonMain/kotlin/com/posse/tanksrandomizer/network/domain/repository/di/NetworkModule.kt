package com.posse.tanksrandomizer.network.domain.repository.di

import com.posse.tanksrandomizer.network.domain.repository.AuthRepository
import com.posse.tanksrandomizer.network.domain.repository.AuthRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val networkModule = DI.Module("NetworkModule") {
    bind<AuthRepository>() with singleton {
        AuthRepositoryImpl()
    }
}