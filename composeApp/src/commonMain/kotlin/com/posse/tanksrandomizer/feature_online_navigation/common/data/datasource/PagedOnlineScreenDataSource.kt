package com.posse.tanksrandomizer.feature_online_navigation.common.data.datasource

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens

interface PagedOnlineScreenDataSource {
    fun getOnlineScreens(): OnlineScreens?
    fun setOnlineScreens(screens: OnlineScreens)

    fun setOnlineScreen(screen: OnlineScreenData)
}
