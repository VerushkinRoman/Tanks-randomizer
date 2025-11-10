package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus

sealed interface OnlineScreenEvent {
    data object ClearAction : OnlineScreenEvent
    data object TrashFilterPressed : OnlineScreenEvent
    data object CheckAllPressed : OnlineScreenEvent
    data object GenerateTankPressed : OnlineScreenEvent
    data object RefreshAccountPressed : OnlineScreenEvent
    data object LogOutPressed : OnlineScreenEvent
    data object ConfirmLogout : OnlineScreenEvent
    data object DismissLogout : OnlineScreenEvent
    data class FilterItemChanged(val item: ItemStatus<*>) : OnlineScreenEvent
}
