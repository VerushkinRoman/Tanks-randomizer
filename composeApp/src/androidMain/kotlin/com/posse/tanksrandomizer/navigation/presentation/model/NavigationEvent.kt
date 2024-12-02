package com.posse.tanksrandomizer.navigation.presentation.model

sealed interface NavigationEvent {
    data object ClearAction : NavigationEvent
    data object OnClosePress : NavigationEvent
}