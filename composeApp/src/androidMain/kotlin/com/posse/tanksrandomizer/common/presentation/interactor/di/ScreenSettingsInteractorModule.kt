package com.posse.tanksrandomizer.common.presentation.interactor.di

import com.posse.tanksrandomizer.common.presentation.interactor.ScreenSettingsInteractor
import com.posse.tanksrandomizer.common.presentation.interactor.ScreenSettingsInteractorImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val screenSettingsInteractorModule = DI.Module("ScreenSettingsInteractorModule") {
    bind<ScreenSettingsInteractor>() with singleton {
        ScreenSettingsInteractorImpl(instance())
    }
}