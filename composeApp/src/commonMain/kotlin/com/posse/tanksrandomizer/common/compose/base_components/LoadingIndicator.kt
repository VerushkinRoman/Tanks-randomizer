package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap

@Composable
expect fun LoadingIndicator(
    modifier: Modifier = Modifier
)

@Composable
fun CommonLoadingIndicator(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.onPrimary,
        trackColor = MaterialTheme.colorScheme.primary,
        strokeCap = StrokeCap.Round,
        modifier = modifier
    )
}
