package com.posse.tanksrandomizer.feature_offline_screen.presentation.models

data class OfflineScreenState(
    val offlineFilters: OfflineFilters,
    val previousFilters: OfflineFilters,
    val numbers: Numbers,
) {
    fun updateFilters(newFilters: OfflineFilters) = copy(
        offlineFilters = newFilters,
        previousFilters = newFilters
    )

    fun updateQuantity(newQuantity: Int) = copy(
        numbers = numbers.updateQuantity(newQuantity)
    )

    fun updateGeneratedQuantity() = copy(
        numbers = numbers.updateGeneratedQuantity()
    )
}
