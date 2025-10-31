package com.posse.tanksrandomizer.feature_main_screen.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.compose.utils.getHorizontalEvenSafeContentPaddings
import com.posse.tanksrandomizer.common.compose.utils.showError
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_main_screen.compose.components.MainScreenContent
import com.posse.tanksrandomizer.feature_main_screen.presentation.MainScreenViewModel
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenAction
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenEvent

@Composable
fun MainScreen(
    toOfflineScreen: () -> Unit,
    toWebViewScreen: (url: String) -> Unit,
    modifier: Modifier,
) {
    val viewModel = viewModel { MainScreenViewModel() }

    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.obtainEvent(MainScreenEvent.OnScreenLaunch)
    }

    LaunchedEffect(action) {
        action?.let { action ->
            when (action) {
                is MainScreenAction.ToOfflineScreen -> toOfflineScreen()
                is MainScreenAction.OpenUrl -> toWebViewScreen(action.url)
                is MainScreenAction.ShowError -> showError(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    error = action.error
                )
            }

            viewModel.obtainEvent(MainScreenEvent.ClearAction)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        MainScreenContent(
            viewState = state,
            onEvent = viewModel::obtainEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = getHorizontalEvenSafeContentPaddings()),
        )
    }
}
