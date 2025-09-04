package com.posse.tanksrandomizer.feature_main_screen.presentation.models

sealed interface MainScreenEvent {
    data object ClearAction : MainScreenEvent
    data object LogIn : MainScreenEvent
    data object ToOfflineScreen : MainScreenEvent
}
