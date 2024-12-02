package com.posse.tanksrandomizer.common.presentation.interactor.di

import com.posse.tanksrandomizer.common.presentation.interactor.FullScreenModeInteractor
import com.posse.tanksrandomizer.common.presentation.interactor.FullScreenModeInteractorImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val fullScreenModeModule = DI.Module("FullScreenModeModule") {
    bind<FullScreenModeInteractor>() with singleton {
        FullScreenModeInteractorImpl(instance())
    }
}