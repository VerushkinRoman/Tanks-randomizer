package com.posse.tanksrandomizer.feature_offline_pane.presentation.models

import com.posse.tanksrandomizer.common.domain.utils.BoxedInt

data class Numbers(
    val quantity: Int = 1,
    val generatedQuantity: BoxedInt = BoxedInt(1),
)
