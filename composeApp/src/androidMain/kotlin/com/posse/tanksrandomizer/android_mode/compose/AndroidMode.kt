package com.posse.tanksrandomizer.android_mode.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.App
import com.posse.tanksrandomizer.android_mode.compose.components.AndroidModeContent
import com.posse.tanksrandomizer.android_mode.presentation.AndroidModeViewModel
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeAction
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent
import com.posse.tanksrandomizer.common.compose.components.startFullScreenMode
import com.posse.tanksrandomizer.common.compose.components.startWindowMode

@Composable
fun AndroidMode(
    viewModel: AndroidModeViewModel = viewModel(),
    startedFromService: Boolean,
    startedAsService: Boolean,
    exitApp: () -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
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

    AndroidModeContent(
        startedFromService = startedFromService,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}
