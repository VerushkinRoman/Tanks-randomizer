package com.posse.tanksrandomizer

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.components.AppBackground
import com.posse.tanksrandomizer.common.compose.theme.AppTheme
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize

@Composable
internal fun CommonPlatformApp(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) = AppTheme {
    AppBackground(
        modifier = modifier,
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            CompositionLocalProvider(
                LocalElementSize provides getElementSize(maxBoxWidth = maxWidth),
            ) {
                content()
            }
        }
    }
}

@Composable
private fun getElementSize(maxBoxWidth: Dp): Dp {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val safeDrawing = WindowInsets.safeDrawing
    val left = with(density) { safeDrawing.getLeft(density, layoutDirection).toDp() }
    val right = with(density) { safeDrawing.getRight(density, layoutDirection).toDp() }
    val maxWidth = maxBoxWidth - left - right
    val elementWidthByScreen = (maxWidth - (20 * 6).dp) / 12
    val elementSize = minOf(elementWidthByScreen, ButtonDefaults.MinHeight)
    return elementSize
}
