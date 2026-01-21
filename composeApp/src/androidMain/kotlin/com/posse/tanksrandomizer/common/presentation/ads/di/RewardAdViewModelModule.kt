package com.posse.tanksrandomizer.common.presentation.ads.di

import com.posse.tanksrandomizer.common.presentation.ads.RewardAdViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.factory
import org.kodein.di.instance

val rewardAdViewModelModule = DI.Module("RewardAdViewModelModule") {
    bind<RewardAdViewModel>() with factory { asService: Boolean ->
        RewardAdViewModel(
            appAsService = asService,
            adRepository = instance(),
            settingsInteractor = instance(),
        )
    }
}
