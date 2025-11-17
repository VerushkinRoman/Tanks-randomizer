package com.posse.tanksrandomizer.feature_offline_screen.presentation.models

data class OfflineScreenState(
    val offlineFilters: OfflineFilters,
    val previousFilters: OfflineFilters? = null,
    val numbers: Numbers,
) {
    fun updateFilters(newFilters: OfflineFilters) = copy(
        offlineFilters = newFilters,
        previousFilters = offlineFilters,
    )

    fun updateQuantity(newQuantity: Int) = copy(
        numbers = numbers.updateQuantity(newQuantity)
    )

    fun updateGeneratedQuantity() = copy(
        numbers = numbers.updateGeneratedQuantity()
    )
}
