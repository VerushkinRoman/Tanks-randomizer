package com.posse.tanksrandomizer.common.compose.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
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
val LocalSizeClass =
    staticCompositionLocalOf<WindowSizeClass> { error("no default implementation") }

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

@Composable
fun getScreenSize(): ScreenSize {
    val windowSizeClass = LocalSizeClass.current

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
