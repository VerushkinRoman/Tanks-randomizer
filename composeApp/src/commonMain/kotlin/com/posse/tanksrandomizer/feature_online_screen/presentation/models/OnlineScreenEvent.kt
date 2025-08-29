package com.posse.tanksrandomizer.feature_online_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus

sealed interface OnlineScreenEvent {
    data object ClearAction : OnlineScreenEvent
    data object TrashFilterPressed : OnlineScreenEvent
    data object GenerateTankPressed : OnlineScreenEvent
    data object SettingsPressed : OnlineScreenEvent
    data object RefreshAccountPressed : OnlineScreenEvent
    class FilterItemChanged<T : ItemStatus<T>>(val item: T) : OnlineScreenEvent
}
