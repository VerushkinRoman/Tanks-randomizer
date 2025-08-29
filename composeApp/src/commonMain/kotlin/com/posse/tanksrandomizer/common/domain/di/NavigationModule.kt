package com.posse.tanksrandomizer.common.domain.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.common.domain.repository.NavigationRepository
import com.posse.tanksrandomizer.common.domain.repository.NavigationRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val navigationModule = DI.Module("NavigationModule") {
    bind<NavigationRepository>() with singleton {
        NavigationRepositoryImpl(
            offlineCommonDataSource = instance(tag = DataSourceType.Offline)
        )
    }
}
