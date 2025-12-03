package com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens
import kotlinx.coroutines.flow.StateFlow

interface OnlineScreensInteractor {
    val onlineScreens: StateFlow<OnlineScreens>

    fun setOnlineScreens(screens: OnlineScreens)

    fun getOnlineScreen(screenId: String): OnlineScreenData?
    fun replaceOnlineScreen(screen: OnlineScreenData)
}
