package com.posse.tanksrandomizer.feature_offline_screen.data.di

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSourceImpl
import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.feature_offline_screen.data.datasource.OfflineScreenDataSource
import com.posse.tanksrandomizer.feature_offline_screen.data.datasource.OfflineScreenDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val offlineScreenDataSourceModule = DI.Module("OfflineScreenDataSourceModule") {
    bind<OfflineDataSource>(tag = DataSourceFor.OfflineScreen) with provider {
        OfflineDataSourceImpl(
            settings = instance(),
            dataSourceFor = DataSourceFor.OfflineScreen,
        )
    }

    bind<OfflineScreenDataSource>() with provider {
        OfflineScreenDataSourceImpl(
            settings = instance(),
        )
    }
}
