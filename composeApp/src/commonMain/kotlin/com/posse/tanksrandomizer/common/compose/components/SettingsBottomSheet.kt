package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
expect fun SettingsBottomSheet(
    showRotation: Boolean,
    showFloatingButtonSettings: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable ((paddingValues: PaddingValues, bottomSheetState: SheetState, snackbarHostState: SnackbarHostState) -> Unit)
)
