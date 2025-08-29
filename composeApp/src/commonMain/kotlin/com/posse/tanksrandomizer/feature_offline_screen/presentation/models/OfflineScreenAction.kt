package com.posse.tanksrandomizer.feature_offline_screen.presentation.models

sealed interface OfflineScreenAction {
    data object ToggleSettings : OfflineScreenAction
}