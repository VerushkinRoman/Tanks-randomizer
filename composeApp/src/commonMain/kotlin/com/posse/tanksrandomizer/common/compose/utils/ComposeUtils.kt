package com.posse.tanksrandomizer.common.compose.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import com.posse.tanksrandomizer.common.domain.utils.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val invisibleModifier = Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.measuredWidth, placeable.measuredHeight) {

    }
}

val LocalElementSize = staticCompositionLocalOf<Dp> { error("no default implementation") }
val LocalMaxWidth = staticCompositionLocalOf<Dp> { error("no default implementation") }
val LocalMaxHeight = staticCompositionLocalOf<Dp> { error("no default implementation") }

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
