package com.posse.tanksrandomizer.feature_online_navigation.common.presentation.di

import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.OnlineScreensInteractor
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.OnlineScreensInteractorImpl
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.TokenInteractor
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.TokenInteractorImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val onlineNavigationInteractorModule = DI.Module("OnlineNavigationInteractorModule"){
    bind<OnlineScreensInteractor>() with singleton {
        OnlineScreensInteractorImpl(
            pagedOnlineScreenRepository = instance(),
        )
    }

    bind<TokenInteractor>() with singleton {
        TokenInteractorImpl(
            onlineScreensInteractor = instance(),
            accountRepository = instance(),
            dispatchers = instance(),
        )
    }
}
