package com.posse.tanksrandomizer.feature_online_screen.domain.repository.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.common.domain.models.RepositoryType
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.repository.AccountRepositoryImpl
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepositoryImpl
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineRepository
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val onlineRepositoryModule = DI.Module("OnlineRepositoryModule") {
    bind<OnlineRepository>() with provider {
        OnlineRepositoryImpl(
            offlineCommonDataSource = instance(tag = DataSourceType.Online),
            onlineDataSource = instance(),
            networkDataSource = instance(),
        )
    }

    bind<CommonTanksRepository>(tag = RepositoryType.Online) with provider {
        CommonTanksRepositoryImpl(offlineCommonDataSource = instance(tag = DataSourceType.Online))
    }

    bind<AccountRepository>() with provider {
        AccountRepositoryImpl(
            offlineCommonDataSource = instance(tag = DataSourceType.Online),
            networkDataSource = instance()
        )
    }
}
