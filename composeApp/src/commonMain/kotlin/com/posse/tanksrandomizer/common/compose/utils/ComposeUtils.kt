package com.posse.tanksrandomizer.common.compose.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.SnackbarHostState
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
