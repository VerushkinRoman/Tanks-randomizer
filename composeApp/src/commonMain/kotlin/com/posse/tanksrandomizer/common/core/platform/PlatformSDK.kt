package com.posse.tanksrandomizer.common.core.platform

import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.data.di.dataSourceModule
import com.posse.tanksrandomizer.common.data.di.ktorModule
import com.posse.tanksrandomizer.common.domain.di.dispatchersModule
import com.posse.tanksrandomizer.common.domain.di.navigationModule
import com.posse.tanksrandomizer.feature_offline_screen.data.di.offlineOfflineDataSourceModule
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.di.offlineRepositoryModule
import com.posse.tanksrandomizer.feature_online_screen.data.di.onlineOfflineCommonDataSourceModule
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.di.onlineRepositoryModule
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
                    offlineOfflineDataSourceModule,
                    onlineRepositoryModule,
                    onlineOfflineCommonDataSourceModule,
                    navigationModule,
                )
            }.direct
        )
    }
}

internal expect val platformModule: DI.Module
