package com.posse.tanksrandomizer.feature_offline_screen.data.di

import com.posse.tanksrandomizer.common.data.CommonDataSourceMultiplatformSettings
import com.posse.tanksrandomizer.common.data.OfflineCommonDataSource
import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.feature_offline_screen.data.OfflineDataSource
import com.posse.tanksrandomizer.feature_offline_screen.data.OfflineDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val offlineOfflineDataSourceModule = DI.Module("OfflineDataSourceModule") {
    bind<OfflineCommonDataSource>(tag = DataSourceType.Offline) with provider {
        CommonDataSourceMultiplatformSettings(
            settings = instance(),
            dataSourceType = DataSourceType.Offline,
        )
    }

    bind<OfflineDataSource>() with provider {
        OfflineDataSourceImpl(
            settings = instance(),
        )
    }
}
