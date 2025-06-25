package com.posse.tanksrandomizer.feature_offline_pane.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_offline_pane.compose.components.OfflinePaneContent
import com.posse.tanksrandomizer.feature_offline_pane.presentation.OfflinePaneViewModel
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneAction
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneEvent

@Composable
fun OfflinePane(
    toggleSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel { OfflinePaneViewModel() }

    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    LaunchedEffect(action) {
        when (action) {
            OfflinePaneAction.ToggleSettings -> toggleSettings()
            else -> Unit
        }
        viewModel.obtainEvent(OfflinePaneEvent.ClearAction)
    }

    OfflinePaneContent(
        viewState = state,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}