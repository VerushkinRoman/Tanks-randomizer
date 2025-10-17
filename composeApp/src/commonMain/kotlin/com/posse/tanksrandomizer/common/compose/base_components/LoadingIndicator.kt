package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap

@Composable
expect fun LoadingIndicator(
    loading: Boolean,
    modifier: Modifier = Modifier
)

@Composable
fun CommonLoadingIndicator(
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = loading,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier,
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onPrimary,
            trackColor = MaterialTheme.colorScheme.primary,
            strokeCap = StrokeCap.Round,
        )
    }
}
