package com.posse.tanksrandomizer.feature_main_screen.presentation.model

import com.posse.tanksrandomizer.common.domain.utils.BoxedInt

data class Numbers(
    val quantity: Int = 1,
    val generatedQuantity: BoxedInt = BoxedInt(1),
)
