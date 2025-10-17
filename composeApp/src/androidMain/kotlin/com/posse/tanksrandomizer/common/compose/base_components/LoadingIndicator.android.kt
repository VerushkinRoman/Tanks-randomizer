package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun LoadingIndicator(
    loading: Boolean,
    modifier: Modifier
) {
    AnimatedVisibility(
        visible = loading,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier,
    ) {
        LoadingIndicator(
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
        )
    }
}
