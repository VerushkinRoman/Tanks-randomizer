package com.posse.tanksrandomizer.common.core.platform

import com.posse.tanksrandomizer.common.data.di.screenSettingsDataSourceModule
import com.posse.tanksrandomizer.common.domain.repository.di.screenSettingsRepositoryModule
import com.posse.tanksrandomizer.common.presentation.interactor.di.screenSettingsInteractorModule
import org.kodein.di.DI

internal actual val platformModule: DI.Module = DI.Module("AndroidModule") {
    importAll(
        screenSettingsDataSourceModule,
        screenSettingsRepositoryModule,
        screenSettingsInteractorModule,
    )
}