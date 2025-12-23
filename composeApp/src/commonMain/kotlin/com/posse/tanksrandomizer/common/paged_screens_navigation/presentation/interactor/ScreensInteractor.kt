package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.interactor

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import kotlinx.coroutines.flow.StateFlow

interface ScreensInteractor {
    val screens: StateFlow<List<PagedScreen<*>>>
    fun setScreens(screens: List<PagedScreen<*>>)
}
