package com.posse.tanksrandomizer.feature_offline_screen.domain.repository.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepositoryImpl
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineScreenRepository
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineScreenRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val offlineScreenRepositoryModule = DI.Module("OfflineScreenRepositoryModule") {
    bind<OfflineScreenRepository>() with provider {
        OfflineScreenRepositoryImpl(
            offlineDataSource = instance(tag = DataSourceFor.OfflineScreen),
            offlineScreenDataSource = instance(),
        )
    }

    bind<CommonTanksRepository>(tag = RepositoryFor.OfflineScreen) with provider {
        CommonTanksRepositoryImpl(
            offlineDataSource = instance(tag = DataSourceFor.OfflineScreen)
        )
    }
}
