package com.posse.tanksrandomizer.common.core.platform

import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.data.di.dataSourceModule
import com.posse.tanksrandomizer.common.data.di.ktorModule
import com.posse.tanksrandomizer.common.domain.utils.di.dispatchersModule
import com.posse.tanksrandomizer.feature_offline_pane.data.di.offlineDataSourceModule
import com.posse.tanksrandomizer.feature_offline_pane.domain.repository.di.offlineRepositoryModule
import com.posse.tanksrandomizer.feature_online_pane.data.di.onlineDataSourceModule
import com.posse.tanksrandomizer.feature_online_pane.domain.repository.di.onlineRepositoryModule
import com.posse.tanksrandomizer.network.domain.repository.di.networkModule
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
                    ktorModule,
                    dataSourceModule,
                    dispatchersModule,
                    platformModule,
                    networkModule,
                    offlineRepositoryModule,
                    offlineDataSourceModule,
                    onlineRepositoryModule,
                    onlineDataSourceModule,
                )
            }.direct
        )
    }
}

internal expect val platformModule: DI.Module