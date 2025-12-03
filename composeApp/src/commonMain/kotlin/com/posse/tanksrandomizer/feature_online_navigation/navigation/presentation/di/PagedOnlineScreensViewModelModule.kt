package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.di

import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.PagedOnlineScreensViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val pagedOnlineScreensViewModelModule = DI.Module("PagedOnlineScreensViewModelModule") {
    bindProvider<PagedOnlineScreensViewModel> {
        PagedOnlineScreensViewModel(
            accountRepository = instance(),
            onlineScreensInteractor = instance(),
            onlineScreenRepository = instance(),
        )
    }
}
