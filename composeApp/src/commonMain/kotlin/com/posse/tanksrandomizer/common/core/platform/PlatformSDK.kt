package com.posse.tanksrandomizer.common.core.platform

import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.data.di.dataSourceModule
import com.posse.tanksrandomizer.common.domain.utils.di.dispatchersModule
import com.posse.tanksrandomizer.feature_main_screen.domain.repository.di.mainRepositoryModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.singleton

object PlatformSDK {
    fun init(configuration: PlatformConfiguration) {
        val umbrellaModule = DI.Module("umbrellaModule") {
            bind<PlatformConfiguration>() with singleton { configuration }
        }

        Inject.createDependencies(
            DI {
                importAll(
                    umbrellaModule,
                    dataSourceModule,
                    mainRepositoryModule,
                    dispatchersModule,
                    platformModule,
                )
            }.direct
        )
    }
}

internal expect val platformModule: DI.Module