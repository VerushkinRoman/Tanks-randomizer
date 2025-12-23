package com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.compose.components.OfflineScreenContent
import com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.presentation.OfflineScreenViewModel
import org.kodein.di.compose.viewmodel.rememberViewModel

@Composable
fun OfflineScreen(
    screenId: String,
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier
) {
    val viewModel: OfflineScreenViewModel by rememberViewModel(arg = screenId)
    val state by viewModel.viewStates().collectAsStateWithLifecycle()

    OfflineScreenContent(
        viewState = state,
        runningAsOverlay = runningAsOverlay,
        onEvent = viewModel::obtainEvent,
        modifier = modifier,
    )
}
