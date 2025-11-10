package com.posse.tanksrandomizer.feature_offline_screen.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.compose.utils.getHorizontalEvenSafeContentPaddings
import com.posse.tanksrandomizer.feature_offline_screen.compose.components.OfflineScreenContent
import com.posse.tanksrandomizer.feature_offline_screen.presentation.OfflineScreenViewModel

@Composable
fun OfflineScreen(
    viewModel: OfflineScreenViewModel,
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier
) {
    val state by viewModel.viewStates().collectAsStateWithLifecycle()

    OfflineScreenContent(
        viewState = state,
        runningAsOverlay = runningAsOverlay,
        onEvent = viewModel::obtainEvent,
        modifier = modifier.padding(horizontal = getHorizontalEvenSafeContentPaddings()),
    )
}
