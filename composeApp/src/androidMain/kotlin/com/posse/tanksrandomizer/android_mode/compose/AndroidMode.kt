package com.posse.tanksrandomizer.android_mode.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.App
import com.posse.tanksrandomizer.android_mode.compose.components.AndroidModeContent
import com.posse.tanksrandomizer.android_mode.presentation.AndroidModeViewModel
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeAction
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent
import com.posse.tanksrandomizer.common.compose.components.startFullScreenMode
import com.posse.tanksrandomizer.common.compose.components.startWindowMode

@Composable
fun AndroidMode(
    startedFromService: Boolean,
    startedAsService: Boolean,
    exitApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = remember { AndroidModeViewModel() }
    val state by viewModel.viewStates().collectAsStateWithLifecycle()

    val context = LocalContext.current
    val windowMode by remember(state.fullScreenModeEnabled, context) {
        mutableStateOf(
            !state.fullScreenModeEnabled
                    && App.canDrawOverlay()
        )
    }

    LaunchedEffect(windowMode, startedAsService) {
        if (windowMode) {
            startWindowMode(context, startedAsService = false)
        } else {
            startFullScreenMode(context)
        }
    }

    LaunchedEffect(startedAsService) {
        if (startedAsService) viewModel.obtainEvent(AndroidModeEvent.OnStartedAsService)
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

    DisposableEffect(viewModel) {
        onDispose {
            viewModel.onCleared()
        }
    }

    AndroidModeContent(
        startedFromService = startedFromService,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}
