package com.posse.tanksrandomizer.feature_online_screen.data.di

import com.posse.tanksrandomizer.common.data.OfflineCommonDataSource
import com.posse.tanksrandomizer.common.data.CommonDataSourceMultiplatformSettings
import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.feature_online_screen.data.NetworkDataSource
import com.posse.tanksrandomizer.feature_online_screen.data.NetworkDataSourceImpl
import com.posse.tanksrandomizer.feature_online_screen.data.OnlineDataSource
import com.posse.tanksrandomizer.feature_online_screen.data.OnlineDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val onlineOfflineCommonDataSourceModule = DI.Module("OnlineDataSourceModule") {
    bind<OfflineCommonDataSource>(tag = DataSourceType.Online) with provider {
        CommonDataSourceMultiplatformSettings(
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
            httpClient = instance(),
            accountRepository = instance()
        )
    }
}
