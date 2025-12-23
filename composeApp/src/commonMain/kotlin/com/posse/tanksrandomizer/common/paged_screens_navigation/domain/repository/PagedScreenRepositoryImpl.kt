package com.posse.tanksrandomizer.common.paged_screens_navigation.domain.repository

import com.posse.tanksrandomizer.common.paged_screens_navigation.data.datasource.PagedScreenDataSource
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen

class PagedScreenRepositoryImpl(
    private val dataSource: PagedScreenDataSource
) : PagedScreenRepository {
    override fun getScreens(): List<PagedScreen<*>>? = dataSource.getScreens()
    override fun setScreens(screens: List<PagedScreen<*>>) = dataSource.setScreens(screens)
    override fun setScreen(screen: PagedScreen<*>) = dataSource.setScreen(screen)
}