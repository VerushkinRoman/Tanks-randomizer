package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.compose.utils.showError
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.models.OnlineScreenNavigationData
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.components.LogoutDialog
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.components.OnlineScreenContent
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.OnlineScreenViewModel
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenAction
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenEvent
import org.kodein.di.compose.viewmodel.rememberViewModel

@Composable
fun OnlineScreen(
    navigationData: OnlineScreenNavigationData,
    logOut: () -> Unit,
    runningAsOverlay: Boolean,
    modifier: Modifier,
) {
    val viewModel: OnlineScreenViewModel by rememberViewModel(arg = navigationData)
    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(action) {
        action?.let { onlineScreenAction ->
            when (onlineScreenAction) {
                OnlineScreenAction.LogOut -> logOut()

                is OnlineScreenAction.ShowError -> showError(
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    error = onlineScreenAction.error,
                )
            }

            viewModel.obtainEvent(OnlineScreenEvent.ClearAction)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
        ) {
            OnlineScreenContent(
                viewState = state,
                runningAsOverlay = runningAsOverlay,
                onEvent = viewModel::obtainEvent,
                modifier = Modifier.fillMaxSize(),
            )
        }

        if (state.logoutDialogVisible) {
            LogoutDialog(
                onConfirm = { viewModel.obtainEvent(OnlineScreenEvent.ConfirmLogout) },
                onDismiss = { viewModel.obtainEvent(OnlineScreenEvent.DismissLogout) },
            )
        }
    }
}
