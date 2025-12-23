package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun VerticalScrollbar(
    scrollState: ScrollState,
    modifier: Modifier
) {
    VerticalScrollbar(
        adapter = rememberScrollbarAdapter(scrollState),
        style = LocalScrollbarStyle.current.copy(
            hoverColor = MaterialTheme.colorScheme.primary,
            unhoverColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = modifier,
    )
}
