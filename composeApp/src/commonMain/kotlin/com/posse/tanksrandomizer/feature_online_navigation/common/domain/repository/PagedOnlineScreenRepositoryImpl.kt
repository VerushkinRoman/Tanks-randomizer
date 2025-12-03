package com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository

import com.posse.tanksrandomizer.feature_online_navigation.common.data.datasource.PagedOnlineScreenDataSource
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens

class PagedOnlineScreenRepositoryImpl(
    private val dataSource: PagedOnlineScreenDataSource
) : PagedOnlineScreenRepository {
    override fun getOnlineScreens(): OnlineScreens? = dataSource.getOnlineScreens()
    override fun setOnlineScreens(screens: OnlineScreens) = dataSource.setOnlineScreens(screens)

    override fun setOnlineScreen(screen: OnlineScreenData) = dataSource.setOnlineScreen(screen)
}
