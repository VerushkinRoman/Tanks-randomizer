package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.posse.tanksrandomizer.common.compose.components.getNavAnimation
import com.posse.tanksrandomizer.common.paged_screens_navigation.compose.PagedScreens
import com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.PagedOfflineScreensViewModel
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.PagedOnlineScreensViewModel
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensEvent
import com.posse.tanksrandomizer.feature_settings_screen.compose.SettingsScreen
import com.posse.tanksrandomizer.navigation.compose.models.LoginResult
import com.posse.tanksrandomizer.navigation.presentation.screens.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.OnlineNavigationRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.SettingsScreenRoute
import kotlinx.coroutines.flow.SharedFlow
import org.kodein.di.compose.viewmodel.rememberViewModel

@Composable
fun SingleScreenNavigationHost(
    navBackStack: NavBackStack<NavKey>,
    runningAsOverlay: Boolean,
    portrait: Boolean,
    selectedOrder: Int,
    previousSelectedOrder: Int,
    loginResultFlow: SharedFlow<LoginResult>,
    pagedOnlineScreen: @Composable (screenId: String) -> Unit,
    pagedOfflineScreen: @Composable (screenId: String) -> Unit,
    modifier: Modifier = Modifier,
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
        transitionSpec = getNavAnimation(
            selectedOrder = selectedOrder,
            previousSelectedOrder = previousSelectedOrder,
            portrait = portrait,
        ),
        entryProvider = entryProvider {
            entry<OnlineNavigationRoute> {
                val viewModel: PagedOnlineScreensViewModel by rememberViewModel()

                LaunchedEffect(loginResultFlow) {
                    loginResultFlow.collect { loginResult ->
                        viewModel
                            .obtainEvent(
                                PagedOnlineScreensEvent.OnSuccessLogin(
                                    id = loginResult.id,
                                    name = loginResult.name,
                                    token = loginResult.token,
                                )
                            )
                    }
                }

                PagedScreens(
                    pagedScreen = pagedOnlineScreen,
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            entry<OfflineScreenRoute> {
                val viewModel: PagedOfflineScreensViewModel by rememberViewModel()

                PagedScreens(
                    pagedScreen = pagedOfflineScreen,
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            entry<SettingsScreenRoute> {
                SettingsScreen(
                    runningAsOverlay = runningAsOverlay,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    )
}
