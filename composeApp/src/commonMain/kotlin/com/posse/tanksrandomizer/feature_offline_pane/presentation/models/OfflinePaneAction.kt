package com.posse.tanksrandomizer.feature_offline_pane.presentation.models

sealed interface OfflinePaneAction {
    data object ToggleSettings : OfflinePaneAction
}