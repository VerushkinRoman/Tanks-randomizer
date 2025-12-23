package com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.di

import com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.PagedOfflineScreensViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val pagedOfflineScreensViewModelModule = DI.Module("PagedOfflineScreensViewModelModule") {
    bindProvider<PagedOfflineScreensViewModel> {
        PagedOfflineScreensViewModel(
            screensInteractor = instance(),
        )
    }
}
