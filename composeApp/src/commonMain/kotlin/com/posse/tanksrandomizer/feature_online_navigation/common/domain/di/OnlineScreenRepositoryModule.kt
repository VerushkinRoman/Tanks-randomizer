package com.posse.tanksrandomizer.feature_online_navigation.common.domain.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.repository.AccountRepositoryImpl
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepositoryImpl
import com.posse.tanksrandomizer.common.paged_screens_navigation.domain.repository.PagedScreenRepository
import com.posse.tanksrandomizer.common.paged_screens_navigation.domain.repository.PagedScreenRepositoryImpl
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.singleton

val onlineScreenRepositoryModule = DI.Module("OnlineScreenRepositoryModule") {
    bind<OnlineScreenRepository>() with singleton {
        OnlineScreenRepositoryImpl(
            onlineScreenDataSource = instance(),
            onlineDataSource = instance(),
            offlineDataSource = instance(tag = DataSourceFor.OnlineScreen),
            dispatchers = instance(),
        )
    }

    bindProvider<CommonTanksRepository>(tag = RepositoryFor.OnlineScreen) {
        CommonTanksRepositoryImpl(
            offlineDataSource = instance(tag = DataSourceFor.OnlineScreen)
        )
    }

    bindProvider<AccountRepository> {
        AccountRepositoryImpl(
            onlineDataSource = instance(),
            offlineDataSource = instance(tag = DataSourceFor.Common),
        )
    }

    bindProvider<PagedScreenRepository>(RepositoryFor.OnlineScreen) {
        PagedScreenRepositoryImpl(
            dataSource = instance(DataSourceFor.OnlineScreen)
        )
    }
}
