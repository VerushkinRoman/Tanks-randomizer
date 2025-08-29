package com.posse.tanksrandomizer.feature_online_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.utils.Error

interface OnlineScreenAction {
    data object ToggleSettings : OnlineScreenAction
    data class ShowError(val error: Error) : OnlineScreenAction
}
