package com.posse.tanksrandomizer.feature_offline_screen.presentation.di

import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.feature_offline_screen.presentation.OfflineScreenViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val offlineScreenViewModelModule = DI.Module("OfflineScreenViewModelModule") {
    bindProvider<OfflineScreenViewModel> {
        OfflineScreenViewModel(
            filterRepository = instance(tag = RepositoryFor.OfflineScreen),
            offlineScreenRepository = instance(),
            dispatchers = instance(),
        )
    }
}
