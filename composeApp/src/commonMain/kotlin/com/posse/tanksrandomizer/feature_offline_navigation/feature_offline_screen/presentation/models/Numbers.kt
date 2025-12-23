package com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.presentation.models

import com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.domain.utils.BoxedInt
import kotlin.random.Random

data class Numbers(
    val quantity: Int = 1,
    val generatedQuantity: BoxedInt = BoxedInt(1),
) {
    fun updateQuantity(newQuantity: Int) = copy(
        quantity = newQuantity.coerceIn(1, 999)
    )

    fun updateGeneratedQuantity(): Numbers {
        val newGeneratedQuantity = when {
            quantity == 1 -> quantity
            else -> generateSequence { Random.nextInt(quantity) + 1 }
                .first {
                    isNumberAllowed(
                        generatedNumber = it,
                        previousNumber = generatedQuantity.int
                    )
                }
        }

        return copy(generatedQuantity = BoxedInt(newGeneratedQuantity))
    }

    private fun isNumberAllowed(
        generatedNumber: Int,
        previousNumber: Int,
    ): Boolean {
        val allowDuplicate = Random.nextFloat() < 0.3
        val numbersAreDifferent = previousNumber != generatedNumber
        return numbersAreDifferent || allowDuplicate
    }
}
