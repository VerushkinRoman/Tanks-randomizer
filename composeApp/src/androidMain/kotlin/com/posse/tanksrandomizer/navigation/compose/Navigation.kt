package com.posse.tanksrandomizer.navigation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.compose.components.StartFullScreenMode
import com.posse.tanksrandomizer.common.compose.components.StartWindowMode
import com.posse.tanksrandomizer.navigation.compose.components.NavigationContent
import com.posse.tanksrandomizer.navigation.presentation.NavigationViewModel
import com.posse.tanksrandomizer.navigation.presentation.model.NavigationAction
import com.posse.tanksrandomizer.navigation.presentation.model.NavigationEvent

@Composable
fun Navigation(
    exitApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel { NavigationViewModel() }
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
                    NavigationAction.ExitApp -> exitApp()
                }
                viewModel.obtainEvent(NavigationEvent.ClearAction)
            }
        }
    }

    NavigationContent(
        state = state,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}