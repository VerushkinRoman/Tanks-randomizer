package com.posse.tanksrandomizer.feature_offline_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.utils.Error

sealed interface OfflineScreenAction {
    data object ToggleSettings : OfflineScreenAction
    data class LogIn(val url: String) : OfflineScreenAction
    data class ShowError(val error: Error) : OfflineScreenAction
}
