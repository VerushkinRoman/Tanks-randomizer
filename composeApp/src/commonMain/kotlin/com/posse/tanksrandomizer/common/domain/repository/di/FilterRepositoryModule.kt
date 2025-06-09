package com.posse.tanksrandomizer.common.domain.repository.di

import com.posse.tanksrandomizer.common.domain.repository.FilterRepository
import com.posse.tanksrandomizer.common.domain.repository.FilterRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val filterRepositoryModule = DI.Module("FilterRepositoryModule") {
    bind<FilterRepository>() with singleton {
        FilterRepositoryImpl(instance())
    }
}