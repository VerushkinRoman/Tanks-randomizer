package com.posse.tanksrandomizer.feature_main_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.utils.Error

sealed interface MainScreenAction {
    data object ToOfflineScreen : MainScreenAction
    data class OpenUri(val uri: String) : MainScreenAction
    data class ShowError(val error: Error) : MainScreenAction
}
