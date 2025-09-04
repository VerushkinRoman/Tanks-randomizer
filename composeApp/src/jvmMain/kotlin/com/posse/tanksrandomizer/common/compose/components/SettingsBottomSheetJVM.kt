package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun SettingsBottomSheet(
    showRotation: Boolean,
    showFloatingButtonSettings: Boolean,
    modifier: Modifier,
    content: @Composable ((PaddingValues, SheetState) -> Unit)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
    ) {
        content(PaddingValues(0.dp), rememberModalBottomSheetState())
    }
}