package com.posse.tanksrandomizer.feature_offline_pane.presentation.models

sealed interface OfflineScreenAction {
    data object ToggleSettings : OfflineScreenAction
}