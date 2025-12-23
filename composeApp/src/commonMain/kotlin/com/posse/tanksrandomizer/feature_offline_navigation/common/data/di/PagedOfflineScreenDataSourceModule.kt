package com.posse.tanksrandomizer.feature_offline_navigation.common.data.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.common.paged_screens_navigation.data.datasource.PagedScreenDataSource
import com.posse.tanksrandomizer.feature_offline_navigation.common.data.datasource.PagedOfflineScreenDataSourceImpl
import com.russhwolf.settings.ExperimentalSettingsApi
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

@OptIn(ExperimentalSettingsApi::class)
val pagedOfflineScreenDataSourceModule = DI.Module("PagedOfflineScreenDataSourceModule") {
    bindProvider<PagedScreenDataSource>(DataSourceFor.OfflineScreen) {
        PagedOfflineScreenDataSourceImpl(
            settings = instance(),
        )
    }
}
