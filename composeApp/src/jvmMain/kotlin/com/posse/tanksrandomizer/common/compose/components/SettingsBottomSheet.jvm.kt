package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(markerClass = [ExperimentalMaterial3Api::class])
@Composable
actual fun SettingsBottomSheet(
    showRotation: Boolean,
    runningAsOverlay: Boolean,
    modifier: Modifier,
    content: @Composable ((PaddingValues, SheetState, SnackbarHostState) -> Unit)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
    ) {
        content(PaddingValues(0.dp), rememberModalBottomSheetState(), remember { SnackbarHostState() })
    }
}
