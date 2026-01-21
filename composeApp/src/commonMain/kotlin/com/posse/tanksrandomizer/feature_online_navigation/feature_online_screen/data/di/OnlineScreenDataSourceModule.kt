package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.di

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSourceImpl
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSourceImpl
import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.AppDatabase
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.getDatabaseBuilder
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.getRoomDatabase
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.datasource.OnlineScreenDataSource
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.datasource.OnlineScreenDataSourceImpl
import com.russhwolf.settings.ExperimentalSettingsApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.singleton

val onlineScreenDataSourceModule = DI.Module("OnlineScreenDataSourceModule") {
    bind<AppDatabase>() with singleton {
        getRoomDatabase(
            builder = getDatabaseBuilder(instance()),
            dispatchers = instance(),
        )
    }

    bindProvider<OfflineDataSource>(tag = DataSourceFor.OnlineScreen) {
        OfflineDataSourceImpl(
            settings = instance(),
            observableSettings = instance(),
            dataSourceFor = DataSourceFor.OnlineScreen,
        )
    }

    bindProvider<OnlineDataSource> {
        OnlineDataSourceImpl(
            httpClient = instance(),
        )
    }

    @OptIn(ExperimentalSettingsApi::class)
    bindProvider<OnlineScreenDataSource> {
        OnlineScreenDataSourceImpl(
            settings = instance(),
            observableSettings = instance(),
            database = instance(),
        )
    }
}
