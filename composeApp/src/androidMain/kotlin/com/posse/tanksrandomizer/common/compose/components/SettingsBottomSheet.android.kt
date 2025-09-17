package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.feature_settings_pane.compose.SettingsPane

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun SettingsBottomSheet(
    showRotation: Boolean,
    showFloatingButtonSettings: Boolean,
    modifier: Modifier,
    content: @Composable ((paddingValues: PaddingValues, bottomSheetState: SheetState, snackbarHostState: SnackbarHostState) -> Unit)
) {
    val bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    val snackbarHostState = remember { SnackbarHostState() }

    BottomSheetScaffold(
        sheetContent = {
            SettingsPane(
                showRotation = showRotation,
                showFloatingButtonSettings = showFloatingButtonSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        WindowInsets.safeDrawing
                            .only(WindowInsetsSides.Bottom)
                            .asPaddingValues()
                    )
            )
        },
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState),
        sheetPeekHeight = 0.dp,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.scrim,
        modifier = modifier
    ) { paddingValues ->
        content(paddingValues, bottomSheetState, snackbarHostState)
    }
}
