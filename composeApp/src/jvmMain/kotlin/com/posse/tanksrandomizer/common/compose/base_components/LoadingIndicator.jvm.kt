package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun LoadingIndicator(loading: Boolean, modifier: Modifier) =
    CommonLoadingIndicator(loading = loading, modifier = modifier)
