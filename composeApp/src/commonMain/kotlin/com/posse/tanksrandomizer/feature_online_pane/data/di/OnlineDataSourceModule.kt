package com.posse.tanksrandomizer.feature_online_pane.data.di

import com.posse.tanksrandomizer.common.data.DataSource
import com.posse.tanksrandomizer.common.data.DataSourceMultiplatformSettings
import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.feature_online_pane.data.NetworkDataSource
import com.posse.tanksrandomizer.feature_online_pane.data.NetworkDataSourceImpl
import com.posse.tanksrandomizer.feature_online_pane.data.OnlineDataSource
import com.posse.tanksrandomizer.feature_online_pane.data.OnlineDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val onlineDataSourceModule = DI.Module("OnlineDataSourceModule") {
    bind<DataSource>(tag = DataSourceType.Online) with provider {
        DataSourceMultiplatformSettings(
            settings = instance(),
            dataSourceType = DataSourceType.Online,
        )
    }

    bind<OnlineDataSource>() with provider {
        OnlineDataSourceImpl(
            settings = instance(),
        )
    }

    bind<NetworkDataSource>() with provider {
        NetworkDataSourceImpl(
            httpClient = instance()
        )
    }
}