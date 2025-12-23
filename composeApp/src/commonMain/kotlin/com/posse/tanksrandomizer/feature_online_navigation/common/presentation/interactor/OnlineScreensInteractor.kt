package com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.interactor.ScreensInteractor
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import kotlinx.coroutines.flow.StateFlow

interface OnlineScreensInteractor : ScreensInteractor {
    override val screens: StateFlow<List<OnlineScreenData>>

    override fun setScreens(screens: List<PagedScreen<*>>)

    fun getOnlineScreen(screenId: String): OnlineScreenData?
    fun replaceOnlineScreen(screen: OnlineScreenData)
}
