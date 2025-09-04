package com.posse.tanksrandomizer.navigation.presentation.screens

import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenRoute {
    @Serializable
    object MainScreenRoute : ScreenRoute

    @Serializable
    object OfflineScreenRoute : ScreenRoute

    @Serializable
    object OnlineScreenRoute : ScreenRoute

    @Serializable
    data class WebViewScreenRoute(val url: String) : ScreenRoute
}
