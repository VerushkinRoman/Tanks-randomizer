package com.posse.tanksrandomizer.feature_online_pane.domain.repository.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.common.domain.models.RepositoryType
import com.posse.tanksrandomizer.common.domain.repository.CommonRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonRepositoryImpl
import com.posse.tanksrandomizer.feature_online_pane.domain.repository.OnlineRepository
import com.posse.tanksrandomizer.feature_online_pane.domain.repository.OnlineRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val onlineRepositoryModule = DI.Module("OnlineRepositoryModule") {
    bind<OnlineRepository>() with provider {
        OnlineRepositoryImpl(
            dataSource = instance(tag = DataSourceType.Online),
            onlineDataSource = instance(),
            networkDataSource = instance()
        )
    }

    bind<CommonRepository>(tag = RepositoryType.Online) with provider {
        CommonRepositoryImpl(instance(tag = DataSourceType.Online))
    }
}