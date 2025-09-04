package com.posse.tanksrandomizer.navigation.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource

class NavigationRepositoryImpl(
    private val offlineDataSource: OfflineDataSource
): NavigationRepository {
    override fun setCurrentScreenRoute(screenRoute: String) = offlineDataSource.setCurrentScreenRoute(screenRoute)
    override fun getScreenRoute(): String? = offlineDataSource.getScreenRoute()
}
