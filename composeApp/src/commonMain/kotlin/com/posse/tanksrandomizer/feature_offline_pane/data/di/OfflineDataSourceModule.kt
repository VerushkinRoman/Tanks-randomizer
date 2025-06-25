package com.posse.tanksrandomizer.feature_offline_pane.data.di

import com.posse.tanksrandomizer.common.data.DataSource
import com.posse.tanksrandomizer.common.data.DataSourceMultiplatformSettings
import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.feature_offline_pane.data.OfflineDataSource
import com.posse.tanksrandomizer.feature_offline_pane.data.OfflineDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val offlineDataSourceModule = DI.Module("OfflineDataSourceModule") {
    bind<DataSource>(tag = DataSourceType.Offline) with provider {
        DataSourceMultiplatformSettings(
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