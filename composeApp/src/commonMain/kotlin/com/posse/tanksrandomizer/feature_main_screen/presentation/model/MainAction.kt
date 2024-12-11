package com.posse.tanksrandomizer.feature_main_screen.presentation.model

sealed interface MainAction {
    data object ToggleSettings : MainAction
}