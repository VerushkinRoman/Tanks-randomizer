package com.posse.tanksrandomizer.navigation.presentation.screens

import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenRoute {
    @Serializable
    object OnlineNavigationRoute : ScreenRoute

    @Serializable
    object OfflineScreenRoute : ScreenRoute

    @Serializable
    object SettingsScreenRoute : ScreenRoute
}

fun ScreenRoute.toRoute(): String {
    return toString()
        .substringAfter('$')
        .takeWhile { it != '@' }
}
