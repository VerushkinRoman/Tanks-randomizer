package com.posse.tanksrandomizer.common.paged_screens_navigation.data.di

import com.posse.tanksrandomizer.common.paged_screens_navigation.data.datasource.PagedScreenDataSource
import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.common.paged_screens_navigation.data.datasource.PagedOnlineScreenDataSourceImpl
import com.russhwolf.settings.ExperimentalSettingsApi
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

@OptIn(ExperimentalSettingsApi::class)
val pagedOnlineScreenDataSourceModule = DI.Module("PagedOnlineScreenDataSourceModule") {
    bindProvider<PagedScreenDataSource>(DataSourceFor.OnlineScreen) {
        PagedOnlineScreenDataSourceImpl(
            settings = instance(),
        )
    }
}
