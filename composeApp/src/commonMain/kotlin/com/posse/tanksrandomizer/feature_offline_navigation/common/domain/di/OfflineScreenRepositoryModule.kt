package com.posse.tanksrandomizer.feature_offline_navigation.common.domain.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepositoryImpl
import com.posse.tanksrandomizer.common.paged_screens_navigation.domain.repository.PagedScreenRepository
import com.posse.tanksrandomizer.common.paged_screens_navigation.domain.repository.PagedScreenRepositoryImpl
import com.posse.tanksrandomizer.feature_offline_navigation.common.domain.repository.OfflineScreenRepository
import com.posse.tanksrandomizer.feature_offline_navigation.common.domain.repository.OfflineScreenRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.singleton

val offlineScreenRepositoryModule = DI.Module("OfflineScreenRepositoryModule") {
    bind<OfflineScreenRepository>() with singleton {
        OfflineScreenRepositoryImpl(
            offlineDataSource = instance(tag = DataSourceFor.OfflineScreen),
            offlineScreenDataSource = instance(),
        )
    }

    bindProvider<CommonTanksRepository>(tag = RepositoryFor.OfflineScreen) {
        CommonTanksRepositoryImpl(
            offlineDataSource = instance(tag = DataSourceFor.OfflineScreen)
        )
    }

    bindProvider<PagedScreenRepository>(RepositoryFor.OfflineScreen) {
        PagedScreenRepositoryImpl(
            dataSource = instance(DataSourceFor.OfflineScreen)
        )
    }
}
