package com.posse.tanksrandomizer.feature_offline_pane.domain.repository.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.common.domain.models.RepositoryType
import com.posse.tanksrandomizer.common.domain.repository.CommonRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonRepositoryImpl
import com.posse.tanksrandomizer.feature_offline_pane.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_pane.domain.repository.OfflineRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val offlineRepositoryModule = DI.Module("OfflineRepositoryModule") {
    bind<OfflineRepository>() with provider {
        OfflineRepositoryImpl(
            dataSource = instance(tag = DataSourceType.Offline),
            offlineDataSource = instance(),
        )
    }

    bind<CommonRepository>(tag = RepositoryType.Offline) with provider {
        CommonRepositoryImpl(instance(tag = DataSourceType.Offline))
    }
}