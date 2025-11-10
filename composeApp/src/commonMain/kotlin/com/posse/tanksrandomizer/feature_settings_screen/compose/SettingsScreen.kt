package com.posse.tanksrandomizer.feature_settings_screen.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.compose.utils.getHorizontalEvenSafeContentPaddings
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.SettingsScreenContent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.SettingsScreenViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel,
    showRotation: Boolean,
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    LaunchedEffect(action) {
        action?.let {
            when (it) {
                SettingsAction.GoToAppSettings -> openSettings()
            }
            viewModel.obtainEvent(SettingsEvent.ClearAction)
        }
    }

    SettingsScreenContent(
        viewState = state,
        showRotation = showRotation,
        runningAsOverlay = runningAsOverlay,
        onEvent = viewModel::obtainEvent,
        modifier = modifier.padding(horizontal = getHorizontalEvenSafeContentPaddings())
    )
}

expect fun openSettings()
