package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens

import kotlinx.serialization.Serializable

interface OnlineNavigationRoute {
    @Serializable
    object MainScreenRoute : OnlineNavigationRoute

    @Serializable
    object OnlineScreenRoute : OnlineNavigationRoute

    @Serializable
    data class WebViewScreenRoute(val url: String)
}
