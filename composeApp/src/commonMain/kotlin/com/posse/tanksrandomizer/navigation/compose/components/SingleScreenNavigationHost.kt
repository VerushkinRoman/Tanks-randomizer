package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.posse.tanksrandomizer.feature_offline_screen.compose.OfflineScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.compose.OnlineNavigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_settings_screen.compose.SettingsScreen
import com.posse.tanksrandomizer.navigation.presentation.screens.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.OnlineNavigationRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.SettingsScreenRoute

@Composable
fun SingleScreenNavigationHost(
    navBackStack: NavBackStack<NavKey>,
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        backStack = navBackStack,
        contentAlignment = Alignment.Center,
        onBack = {},
        modifier = modifier,
        entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(
                    removeViewModelStoreOnPop = { true }
                ),
            ),
        entryProvider = entryProvider {
            entry<OnlineNavigationRoute> {
                OnlineNavigation(
                    runningAsOverlay = runningAsOverlay,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<OfflineScreenRoute> {
                OfflineScreen(
                    runningAsOverlay = runningAsOverlay,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<SettingsScreenRoute> {
                SettingsScreen(
                    runningAsOverlay = runningAsOverlay,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    )
}
