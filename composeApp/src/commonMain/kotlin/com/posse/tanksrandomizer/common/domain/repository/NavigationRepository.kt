package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.models.ScreenRoute

interface NavigationRepository {
    fun getScreenRoute(): ScreenRoute?
    fun setCurrentScreenRoute(currentScreenRoute: ScreenRoute)
}
