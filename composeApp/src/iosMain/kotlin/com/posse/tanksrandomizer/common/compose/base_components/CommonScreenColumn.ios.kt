package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun VerticalScrollbar(
    scrollState: ScrollState,
    modifier: Modifier
) = Unit

@Composable
actual fun BannerAD(
    runningAsOverlay: Boolean,
    modifier: Modifier
) = Unit
