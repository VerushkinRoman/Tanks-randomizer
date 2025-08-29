package com.posse.tanksrandomizer.feature_offline_screen.domain.repository.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.common.domain.models.RepositoryType
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepositoryImpl
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val offlineRepositoryModule = DI.Module("OfflineRepositoryModule") {
    bind<OfflineRepository>() with provider {
        OfflineRepositoryImpl(
            offlineCommonDataSource = instance(tag = DataSourceType.Offline),
            offlineDataSource = instance(),
        )
    }

    bind<CommonTanksRepository>(tag = RepositoryType.Offline) with provider {
        CommonTanksRepositoryImpl(offlineCommonDataSource = instance(tag = DataSourceType.Offline))
    }
}
