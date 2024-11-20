package com.posse.tanksrandomizer.feature_main_screen.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_main_screen.compose.components.MainScreenContent
import com.posse.tanksrandomizer.feature_main_screen.presentation.MainViewModel
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.MainAction
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.MainEvent

@Composable
fun MainScreen(
    openSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel { MainViewModel() }

    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    LaunchedEffect(action) {
        when (action) {
            MainAction.OpenSettings -> openSettings()
            else -> Unit
        }
        viewModel.obtainEvent(MainEvent.ClearAction)
    }

    MainScreenContent(
        viewState = state,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}