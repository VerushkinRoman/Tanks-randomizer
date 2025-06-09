package com.posse.tanksrandomizer.android_mode.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.compose.components.StartFullScreenMode
import com.posse.tanksrandomizer.common.compose.components.StartWindowMode
import com.posse.tanksrandomizer.android_mode.compose.components.AndroidModeContent
import com.posse.tanksrandomizer.android_mode.presentation.AndroidModeViewModel
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeAction
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent

@Composable
fun AndroidMode(
    startedFromService: Boolean,
    exitApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel { AndroidModeViewModel() }
    val state by viewModel.viewStates().collectAsStateWithLifecycle()

    if (!state.fullScreenModeEnabled) {
        StartWindowMode()
    } else {
        StartFullScreenMode()
    }

    LaunchedEffect(viewModel) {
        viewModel.viewActions().collect { action ->
            action?.let {
                when (it) {
                    AndroidModeAction.ExitApp -> exitApp()
                }
                viewModel.obtainEvent(AndroidModeEvent.ClearAction)
            }
        }
    }

    AndroidModeContent(
        state = state,
        startedFromService = startedFromService,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}