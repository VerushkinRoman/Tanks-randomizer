package com.posse.tanksrandomizer.feature_online_navigation.common.data.di

import com.posse.tanksrandomizer.feature_online_navigation.common.data.datasource.PagedOnlineScreenDataSource
import com.posse.tanksrandomizer.feature_online_navigation.common.data.datasource.PagedOnlineScreenDataSourceImpl
import com.russhwolf.settings.ExperimentalSettingsApi
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

@OptIn(ExperimentalSettingsApi::class)
val pagedOnlineScreenDataSourceModule = DI.Module("PagedOnlineScreenDataSourceModule") {
    bindProvider<PagedOnlineScreenDataSource> {
        PagedOnlineScreenDataSourceImpl(
            settings = instance(),
        )
    }
}
