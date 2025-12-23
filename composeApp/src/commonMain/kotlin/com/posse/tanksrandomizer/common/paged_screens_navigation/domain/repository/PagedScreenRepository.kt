package com.posse.tanksrandomizer.common.paged_screens_navigation.domain.repository

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen

interface PagedScreenRepository {
    fun getScreens(): List<PagedScreen<*>>?
    fun setScreens(screens: List<PagedScreen<*>>)

    fun setScreen(screen: PagedScreen<*>)
}