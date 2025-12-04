package com.posse.tanksrandomizer.feature_online_navigation.common.domain.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.repository.AccountRepositoryImpl
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepositoryImpl
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
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

    bind<CommonTanksRepository>(tag = RepositoryFor.OnlineScreen) with provider {
        CommonTanksRepositoryImpl(
            offlineDataSource = instance(tag = DataSourceFor.OnlineScreen)
        )
    }

    bind<AccountRepository>() with provider {
        AccountRepositoryImpl(
            onlineDataSource = instance(),
            offlineDataSource = instance(tag = DataSourceFor.Common),
        )
    }
}
