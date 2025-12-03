package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.di

import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.models.OnlineScreenNavigationData
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.OnlineScreenViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance

val onlineScreenViewModelModule = DI.Module("OnlineScreenViewModelModule") {
    bind<OnlineScreenViewModel>() with factory { navigationData: OnlineScreenNavigationData ->
        OnlineScreenViewModel(
            screenId = navigationData.id,
            accountId = navigationData.accountId,
            filterRepository = instance(tag = RepositoryFor.OnlineScreen),
            onlineScreenRepository = instance(),
            accountRepository = instance(),
            onlineScreensInteractor = instance(),
            interactor = instance(),
            dispatchers = instance(),
        )
    }
}
