package com.posse.tanksrandomizer.navigation.domain.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.navigation.domain.repository.NavigationRepository
import com.posse.tanksrandomizer.navigation.domain.repository.NavigationRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val navigationRepositoryModule = DI.Module("NavigationRepositoryModule") {
    bind<NavigationRepository>() with provider {
        NavigationRepositoryImpl(
            offlineDataSource = instance(tag = DataSourceFor.Common)
        )
    }
}
