package com.posse.tanksrandomizer.common.compose.models

sealed interface AdditionalBottomComponentsEvent {
    data object SettingsPressed : AdditionalBottomComponentsEvent
    data object LogInPressed : AdditionalBottomComponentsEvent
}
