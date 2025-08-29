package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.OfflineCommonDataSource
import com.posse.tanksrandomizer.common.domain.models.ScreenRoute

class NavigationRepositoryImpl(
    private val offlineCommonDataSource: OfflineCommonDataSource,
): NavigationRepository {
    override fun getScreenRoute(): ScreenRoute? = offlineCommonDataSource.getScreenRoute()
    override fun setCurrentScreenRoute(currentScreenRoute: ScreenRoute) {
        offlineCommonDataSource.setCurrentScreenRoute(currentScreenRoute)
    }
}
