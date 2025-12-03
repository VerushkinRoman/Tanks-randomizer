package com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.di

import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.MainScreenViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val mainScreenViewModelModule = DI.Module("MainScreenViewModelModule") {
    bindProvider<MainScreenViewModel> {
        MainScreenViewModel(
            accountRepository = instance(),
        )
    }
}
