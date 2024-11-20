package com.posse.tanksrandomizer

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.theme.AppTheme
import com.posse.tanksrandomizer.common.compose.utils.DeviceType
import com.posse.tanksrandomizer.common.compose.utils.ElementSize
import com.posse.tanksrandomizer.common.compose.utils.LocaleDeviceType
import com.posse.tanksrandomizer.navigation.compose.Navigation

@Composable
internal fun ComposeApp() = AppTheme {
    BoxWithConstraints {
        CompositionLocalProvider(
            ElementSize provides getElementSize(maxBoxWidth = maxWidth),
            LocaleDeviceType provides deviceType,
        ) {
            Navigation(
                modifier = Modifier.fillMaxSize()
            )
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

internal expect val deviceType: DeviceType