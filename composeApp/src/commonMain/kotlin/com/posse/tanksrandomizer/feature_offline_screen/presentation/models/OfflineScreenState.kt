package com.posse.tanksrandomizer.feature_offline_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.utils.BoxedInt
import kotlin.random.Random

data class OfflineScreenState(
    val offlineFilters: OfflineFilters,
    val numbers: Numbers,
) {
    fun updateFilters(newFilters: OfflineFilters): OfflineScreenState {
        return copy(offlineFilters = newFilters)
    }

    fun updateQuantity(newQuantity: Int): OfflineScreenState {
        return copy(
            numbers = numbers.copy(
                quantity = newQuantity.coerceIn(1, 999)
            )
        )
    }

    fun updateGeneratedQuantity(): OfflineScreenState {
        return copy(
            numbers = numbers.copy(
                generatedQuantity = BoxedInt(Random.nextInt(1, numbers.quantity + 1))
            )
        )
    }
}
