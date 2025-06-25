package com.posse.tanksrandomizer.android_mode.compose.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.feature_offline_pane.compose.OfflinePane
import com.posse.tanksrandomizer.feature_settings_pane.compose.SettingsPane
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScaffoldWithBottomSheet(
    showRotation: Boolean,
    showFloatingButtonSettings: Boolean,
    modifier: Modifier = Modifier
) {
    val bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    val scope = rememberCoroutineScope()

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
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.scrim,
        modifier = modifier
    ) {
        OfflinePane(
            toggleSettings = {
                scope.launch {
                    if (bottomSheetState.currentValue == SheetValue.Expanded) {
                        bottomSheetState.hide()
                    } else {
                        bottomSheetState.expand()
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
