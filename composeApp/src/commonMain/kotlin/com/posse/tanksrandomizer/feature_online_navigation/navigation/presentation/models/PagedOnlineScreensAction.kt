package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models

sealed interface PagedOnlineScreensAction {
    data object CantAddScreens : PagedOnlineScreensAction
}
