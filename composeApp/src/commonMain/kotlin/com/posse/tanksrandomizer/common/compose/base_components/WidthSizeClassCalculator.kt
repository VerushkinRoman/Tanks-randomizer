package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun WidthSizeClassCalculator(
    modifier: Modifier = Modifier,
    content: @Composable (WindowWidthSizeClass) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val sizeClass = WindowSizeClass.calculateFromSize(
            DpSize(width = maxWidth, height = maxHeight)
        )

        content(sizeClass.widthSizeClass)
    }
}
