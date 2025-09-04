package com.posse.tanksrandomizer.feature_online_screen.data.di

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSourceImpl
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSourceImpl
import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.feature_online_screen.data.datasource.OnlineScreenDataSource
import com.posse.tanksrandomizer.feature_online_screen.data.datasource.OnlineScreenDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val onlineScreenDataSourceModule = DI.Module("OnlineScreenDataSourceModule") {
    bind<OfflineDataSource>(tag = DataSourceFor.OnlineScreen) with provider {
        OfflineDataSourceImpl(
            settings = instance(),
            dataSourceFor = DataSourceFor.OnlineScreen,
        )
    }

    bind<OnlineDataSource>() with provider {
        OnlineDataSourceImpl(
            httpClient = instance(),
        )
    }

    bind<OnlineScreenDataSource>() with provider {
        OnlineScreenDataSourceImpl(
            settings = instance()
        )
    }
}
