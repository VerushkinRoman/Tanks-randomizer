package com.posse.tanksrandomizer.compose.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

val invisibleModifier = Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.measuredWidth, placeable.measuredHeight) {

    }
}