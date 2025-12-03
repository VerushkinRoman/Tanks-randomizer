package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.di

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSourceImpl
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSourceImpl
import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.datasource.OnlineScreenDataSource
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.datasource.OnlineScreenDataSourceImpl
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.DBEncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.DBMasteryTank
import com.russhwolf.settings.ExperimentalSettingsApi
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val onlineScreenDataSourceModule = DI.Module("OnlineScreenDataSourceModule") {
    bindProvider<Realm> {
        Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    DBEncyclopediaTank::class,
                    DBMasteryTank::class,
                )
            )
        )
    }

    bindProvider<OfflineDataSource>(tag = DataSourceFor.OnlineScreen) {
        OfflineDataSourceImpl(
            settings = instance(),
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
