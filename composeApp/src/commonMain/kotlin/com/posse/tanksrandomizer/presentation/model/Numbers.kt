package com.posse.tanksrandomizer.presentation.model

import com.posse.tanksrandomizer.utils.BoxedInt

data class Numbers(
    val quantity: Int = 1,
    val generatedQuantity: BoxedInt = BoxedInt(1),
)
