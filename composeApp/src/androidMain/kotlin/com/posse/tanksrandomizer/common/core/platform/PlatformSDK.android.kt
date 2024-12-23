package com.posse.tanksrandomizer.common.core.platform

import com.posse.tanksrandomizer.common.data.di.settingsDataSourceModule
import com.posse.tanksrandomizer.common.domain.repository.di.settingsRepositoryModule
import com.posse.tanksrandomizer.common.presentation.interactor.di.settingsInteractorModule
import org.kodein.di.DI

internal actual val platformModule: DI.Module = DI.Module("AndroidModule") {
    importAll(
        settingsDataSourceModule,
        settingsRepositoryModule,
        settingsInteractorModule,
    )
}