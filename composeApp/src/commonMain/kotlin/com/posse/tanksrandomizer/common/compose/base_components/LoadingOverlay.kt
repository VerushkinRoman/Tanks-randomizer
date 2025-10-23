package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingOverlay(
    visible: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .then(
                if (visible) {
                    Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {}
                    )
                } else Modifier
            ),
    ) {
        LoadingIndicator(
            loading = visible,
        )
    }
}
