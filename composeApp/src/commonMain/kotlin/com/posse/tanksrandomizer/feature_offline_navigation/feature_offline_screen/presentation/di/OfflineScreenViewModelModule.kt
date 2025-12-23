package com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.presentation.di

import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.presentation.OfflineScreenViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance

val offlineScreenViewModelModule = DI.Module("OfflineScreenViewModelModule") {
    bind<OfflineScreenViewModel>() with factory { screenId: String ->
        OfflineScreenViewModel(
            id = screenId,
            filterRepository = instance(tag = RepositoryFor.OfflineScreen),
            offlineScreenRepository = instance(),
            dispatchers = instance(),
        )
    }
}
