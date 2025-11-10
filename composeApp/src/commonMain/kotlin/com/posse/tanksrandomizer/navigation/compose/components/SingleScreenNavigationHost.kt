package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.posse.tanksrandomizer.feature_offline_screen.compose.OfflineScreen
import com.posse.tanksrandomizer.feature_offline_screen.presentation.OfflineScreenViewModel
import com.posse.tanksrandomizer.feature_online_navigation.navigation.compose.OnlineNavigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_settings_screen.compose.SettingsScreen
import com.posse.tanksrandomizer.feature_settings_screen.presentation.SettingsScreenViewModel
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OnlineNavigationRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.SettingsScreenRoute

@Composable
fun SingleScreenNavigationHost(
    navController: NavHostController,
    startDestination: ScreenRoute,
    showRotation: Boolean,
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<OfflineScreenRoute> { backStackEntry ->
            val viewModel = remember(backStackEntry) { OfflineScreenViewModel() }

            DisposableEffect(viewModel) {
                onDispose {
                    viewModel.onCleared()
                }
            }

            OfflineScreen(
                viewModel = viewModel,
                runningAsOverlay = runningAsOverlay,
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<OnlineNavigationRoute> {
            OnlineNavigation(
                runningAsOverlay = runningAsOverlay,
                onRedirectError = onRedirectError,
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<SettingsScreenRoute> { backStackEntry ->
            val viewModel = remember(backStackEntry) { SettingsScreenViewModel() }

            DisposableEffect(viewModel) {
                onDispose {
                    viewModel.onCleared()
                }
            }

            SettingsScreen(
                viewModel = viewModel,
                showRotation = showRotation,
                runningAsOverlay = runningAsOverlay,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
