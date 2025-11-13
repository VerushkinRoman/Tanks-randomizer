package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun LoadingIndicator(
    modifier: Modifier
) {
    LoadingIndicator(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier
    )
}
