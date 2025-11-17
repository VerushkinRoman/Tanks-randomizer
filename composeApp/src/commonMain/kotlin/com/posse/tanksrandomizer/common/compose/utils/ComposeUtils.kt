package com.posse.tanksrandomizer.common.compose.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.domain.utils.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val invisibleModifier = Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.measuredWidth, placeable.measuredHeight) {

    }
}

val LocalElementSize = staticCompositionLocalOf<Dp> { error("no default implementation") }
val LocalSizeClass = staticCompositionLocalOf<ScreenSize> { error("no default implementation") }

fun showError(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    error: Error
) {
    scope.launch {
        snackbarHostState.showSnackbar(
            message = ErrorHandler.getErrorMessage(error)
        )
    }
}

@Composable
fun getHorizontalEvenSafeContentPaddings(): Dp {
    val layoutDirection = LocalLayoutDirection.current
    val horizontalPaddings = WindowInsets.safeContent
        .only(WindowInsetsSides.Horizontal)
        .asPaddingValues()
    val startPadding = horizontalPaddings.calculateStartPadding(layoutDirection)
    val endPadding = horizontalPaddings.calculateEndPadding(layoutDirection)

    return maxOf(startPadding, endPadding, 16.dp)
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun getScreenSize(maxWidth: Dp, maxHeight: Dp): ScreenSize {
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(width = maxWidth, height = maxHeight)
    )

    return when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> ScreenSize.Small
        WindowWidthSizeClass.Medium -> ScreenSize.Medium
        WindowWidthSizeClass.Expanded if windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact -> ScreenSize.Large
        else -> ScreenSize.Medium
    }
}

enum class ScreenSize {
    Small,
    Medium,
    Large,
}

@Composable
fun getElementSize(maxBoxWidth: Dp): Dp {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val safeDrawing = WindowInsets.safeDrawing
    val left = with(density) { safeDrawing.getLeft(density, layoutDirection).toDp() }
    val right = with(density) { safeDrawing.getRight(density, layoutDirection).toDp() }
    val maxWidth = maxBoxWidth - left - right
    val elementWidthByScreen = (maxWidth - (20 * 6).dp) / 12
    val elementSize = minOf(elementWidthByScreen, ButtonDefaults.MinHeight * (3f / 4f))
    return elementSize
}
