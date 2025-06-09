package com.posse.tanksrandomizer.android_mode.presentation.model

sealed interface AndroidModeEvent {
    data object ClearAction : AndroidModeEvent
    data object OnClosePress : AndroidModeEvent
}