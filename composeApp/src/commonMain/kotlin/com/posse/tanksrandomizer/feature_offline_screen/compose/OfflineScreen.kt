package com.posse.tanksrandomizer.feature_offline_screen.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.compose.components.SettingsBottomSheet
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_offline_screen.compose.components.OfflineScreenContent
import com.posse.tanksrandomizer.feature_offline_screen.presentation.OfflineScreenViewModel
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenAction
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineScreen(
    toMainScreen: () -> Unit,
    showRotation: Boolean,
    showFloatingButtonSettings: Boolean,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    val viewModel = viewModel { OfflineScreenViewModel() }

    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    SettingsBottomSheet(
        showRotation = showRotation,
        showFloatingButtonSettings = showFloatingButtonSettings,
        modifier = modifier
    ) { paddingValues, bottomSheetState ->
        LaunchedEffect(action) {
            action?.let { offlineScreenAction ->
                when (offlineScreenAction) {
                    OfflineScreenAction.ToggleSettings -> {
                        scope.launch {
                            if (bottomSheetState.currentValue == SheetValue.Expanded) {
                                bottomSheetState.hide()
                            } else {
                                bottomSheetState.expand()
                            }
                        }
                    }

                    OfflineScreenAction.ToMainScreen -> toMainScreen()
                }

                viewModel.obtainEvent(OfflineScreenEvent.ClearAction)
            }
        }

        OfflineScreenContent(
            viewState = state,
            onEvent = viewModel::obtainEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}
