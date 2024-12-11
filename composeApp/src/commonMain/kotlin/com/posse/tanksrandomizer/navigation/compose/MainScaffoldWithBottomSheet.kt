package com.posse.tanksrandomizer.navigation.compose

import androidx.compose.animation.AnimatedContent
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
import com.posse.tanksrandomizer.feature_main_screen.compose.MainScreen
import com.posse.tanksrandomizer.feature_settings_screen.compose.SettingsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScaffoldWithBottomSheet(
    windowInFullScreen: Boolean = true,
    showRotation: Boolean = true,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(
            WindowInsets.safeDrawing
                .only(WindowInsetsSides.Horizontal)
                .asPaddingValues()
        )
) {
    val bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    val scope = rememberCoroutineScope()

    AnimatedContent(
        targetState = windowInFullScreen,
        modifier = modifier
    ) { fullscreen ->
        if (fullscreen) {
            BottomSheetScaffold(
                sheetContent = {
                    SettingsScreen(
                        showRotation = showRotation,
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
                modifier = Modifier.fillMaxSize()
            ) {
                MainScreen(
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
    }
}