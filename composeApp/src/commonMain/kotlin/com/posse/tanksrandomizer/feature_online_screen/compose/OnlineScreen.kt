package com.posse.tanksrandomizer.feature_online_screen.compose

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
import com.posse.tanksrandomizer.common.compose.utils.showError
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_online_screen.compose.components.OnlineScreenContent
import com.posse.tanksrandomizer.feature_online_screen.presentation.OnlineScreenViewModel
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenAction
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineScreen(
    logOut: () -> Unit,
    showRotation: Boolean,
    showFloatingButtonSettings: Boolean,
    modifier: Modifier
) {
    val scope = rememberCoroutineScope()

    val viewModel = viewModel { OnlineScreenViewModel() }

    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    SettingsBottomSheet(
        showRotation = showRotation,
        showFloatingButtonSettings = showFloatingButtonSettings,
        modifier = modifier
    ) { paddingValues, bottomSheetState, snackbarHostState ->
        LaunchedEffect(action) {
            action?.let { onlineScreenAction ->
                when (onlineScreenAction) {
                    OnlineScreenAction.ToggleSettings -> {
                        scope.launch {
                            if (bottomSheetState.currentValue == SheetValue.Expanded) {
                                bottomSheetState.hide()
                            } else {
                                bottomSheetState.expand()
                            }
                        }
                    }

                    OnlineScreenAction.LogOut -> logOut()

                    is OnlineScreenAction.ShowError -> showError(
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        error = onlineScreenAction.error
                    )
                }

                viewModel.obtainEvent(OnlineScreenEvent.ClearAction)
            }
        }

        OnlineScreenContent(
            viewState = state,
            onEvent = viewModel::obtainEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}
