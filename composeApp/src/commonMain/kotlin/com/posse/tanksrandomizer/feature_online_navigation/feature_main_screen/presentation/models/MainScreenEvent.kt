package com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.models

sealed interface MainScreenEvent {
    data object ClearAction : MainScreenEvent
    data object LogIn : MainScreenEvent
    data object OnScreenLaunch : MainScreenEvent
}
