package com.posse.tanksrandomizer.android_mode.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.feature_offline_screen.compose.OfflineScreen
import com.posse.tanksrandomizer.navigation.compose.components.SettingsBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScaffoldWithBottomSheet(
    showRotation: Boolean,
    showFloatingButtonSettings: Boolean,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    SettingsBottomSheet(
        showRotation = showRotation,
        showFloatingButtonSettings = showFloatingButtonSettings,
        modifier = modifier
    ) { paddingValues, bottomSheetState ->
        OfflineScreen(
            toggleSettings = {
                scope.launch {
                    if (bottomSheetState.currentValue == SheetValue.Expanded) {
                        bottomSheetState.hide()
                    } else {
                        bottomSheetState.expand()
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}
