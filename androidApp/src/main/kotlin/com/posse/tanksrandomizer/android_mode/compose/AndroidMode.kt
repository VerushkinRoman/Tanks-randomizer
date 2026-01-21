package com.posse.tanksrandomizer.android_mode.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.android_mode.compose.components.AndroidModeContent
import com.posse.tanksrandomizer.android_mode.presentation.AndroidModeViewModel
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeAction
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent

@Composable
fun AndroidMode(
    viewModel: AndroidModeViewModel = viewModel(),
    startedAsService: Boolean,
    exitApp: () -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
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
        startedAsService = startedAsService,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}
