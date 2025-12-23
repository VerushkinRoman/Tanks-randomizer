package com.posse.tanksrandomizer.common.paged_screens_navigation.data.datasource

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen

interface PagedScreenDataSource {
    fun getScreens(): List<PagedScreen<*>>?
    fun setScreens(screens: List<PagedScreen<*>>)

    fun setScreen(screen: PagedScreen<*>)
}