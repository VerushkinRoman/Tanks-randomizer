package com.posse.tanksrandomizer.feature_offline_navigation.common.presentation.di

import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.interactor.ScreensInteractor
import com.posse.tanksrandomizer.feature_offline_navigation.common.presentation.interactor.OfflineScreensInteractorImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val offlineNavigationInteractorModule = DI.Module("OfflineNavigationInteractorModule") {
    bind<ScreensInteractor>() with singleton {
        OfflineScreensInteractorImpl(
            pagedScreenRepository = instance(RepositoryFor.OfflineScreen),
        )
    }
}
