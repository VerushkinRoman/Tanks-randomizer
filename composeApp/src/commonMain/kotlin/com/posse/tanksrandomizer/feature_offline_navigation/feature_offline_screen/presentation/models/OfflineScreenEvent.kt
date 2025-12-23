package com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus

sealed interface OfflineScreenEvent {
    data object ClearAction : OfflineScreenEvent
    data object TrashFilterPressed : OfflineScreenEvent
    data object CheckAllPressed : OfflineScreenEvent
    data object GenerateFilterPressed : OfflineScreenEvent
    data object GenerateNumberPressed : OfflineScreenEvent
    data object TrashNumberPressed : OfflineScreenEvent
    data class FilterItemChanged(val item: ItemStatus<*>) : OfflineScreenEvent
    data class QuantityChanged(val amount: Int) : OfflineScreenEvent
}
