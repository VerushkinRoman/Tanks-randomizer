package com.posse.tanksrandomizer.feature_offline_pane.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_offline_pane.compose.components.OfflineScreenContent
import com.posse.tanksrandomizer.feature_offline_pane.presentation.OfflineScreenViewModel
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineScreenAction
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineScreenEvent

@Composable
fun OfflineScreen(
    toggleSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel { OfflineScreenViewModel() }

    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    LaunchedEffect(action) {
        when (action) {
            OfflineScreenAction.ToggleSettings -> toggleSettings()
            else -> Unit
        }
        viewModel.obtainEvent(OfflineScreenEvent.ClearAction)
    }

    OfflineScreenContent(
        viewState = state,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}