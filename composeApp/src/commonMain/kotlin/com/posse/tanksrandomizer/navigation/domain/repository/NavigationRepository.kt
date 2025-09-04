package com.posse.tanksrandomizer.navigation.domain.repository

interface NavigationRepository {
    fun setCurrentScreenRoute(screenRoute: String)
    fun getScreenRoute(): String?
}
