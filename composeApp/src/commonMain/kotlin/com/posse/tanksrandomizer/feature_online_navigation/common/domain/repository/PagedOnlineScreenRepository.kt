package com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens

interface PagedOnlineScreenRepository {
    fun getOnlineScreens(): OnlineScreens?
    fun setOnlineScreens(screens: OnlineScreens)

    fun setOnlineScreen(screen: OnlineScreenData)
}
