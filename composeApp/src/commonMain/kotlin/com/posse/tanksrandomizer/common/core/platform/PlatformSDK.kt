package com.posse.tanksrandomizer.common.core.platform

import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.data.di.commonDataSourceModule
import com.posse.tanksrandomizer.common.domain.di.dispatchersModule
import com.posse.tanksrandomizer.feature_offline_navigation.common.data.di.pagedOfflineScreenDataSourceModule
import com.posse.tanksrandomizer.feature_offline_navigation.common.presentation.di.offlineNavigationInteractorModule
import com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.data.di.offlineScreenDataSourceModule
import com.posse.tanksrandomizer.feature_offline_navigation.common.domain.di.offlineScreenRepositoryModule
import com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.presentation.di.offlineScreenViewModelModule
import com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.di.pagedOfflineScreensViewModelModule
import com.posse.tanksrandomizer.feature_online_navigation.common.data.di.pagedOnlineScreenDataSourceModule
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.di.onlineScreenRepositoryModule
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.di.onlineNavigationInteractorModule
import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.di.mainScreenViewModelModule
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.di.onlineScreenDataSourceModule
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.di.onlineScreenViewModelModule
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.di.pagedOnlineScreensViewModelModule
import com.posse.tanksrandomizer.feature_settings_screen.data.di.settingsDataSourceModule
import com.posse.tanksrandomizer.feature_settings_screen.domain.repository.di.settingsRepositoryModule
import com.posse.tanksrandomizer.feature_settings_screen.presentation.di.settingsScreenViewModelModule
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.di.settingsInteractorModule
import com.posse.tanksrandomizer.navigation.domain.di.navigationRepositoryModule
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
                    commonDataSourceModule,
                    dispatchersModule,
                    platformModule,
                    offlineScreenRepositoryModule,
                    offlineScreenDataSourceModule,
                    onlineScreenRepositoryModule,
                    onlineScreenDataSourceModule,
                    navigationRepositoryModule,
                    settingsDataSourceModule,
                    settingsRepositoryModule,
                    settingsInteractorModule,
                    pagedOnlineScreenDataSourceModule,
                    pagedOfflineScreenDataSourceModule,
                    onlineNavigationInteractorModule,
                    offlineNavigationInteractorModule,

                    mainScreenViewModelModule,
                    onlineScreenViewModelModule,
                    offlineScreenViewModelModule,
                    settingsScreenViewModelModule,
                    pagedOnlineScreensViewModelModule,
                    pagedOfflineScreensViewModelModule,
                )
            }.direct
        )
    }
}

internal expect val platformModule: DI.Module
