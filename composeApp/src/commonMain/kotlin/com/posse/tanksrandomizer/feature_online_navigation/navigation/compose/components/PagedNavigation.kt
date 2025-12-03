package com.posse.tanksrandomizer.feature_online_navigation.navigation.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensState
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page0Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page1Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page2Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page3Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page4Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page5Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page6Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page7Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page8Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.Page9Navigation
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.pagedNavigationConfiguration

@Composable
internal fun PagedNavigation(
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    onSuccessLogin: (id:String, name:String, token:Token)-> Unit,
    state: PagedOnlineScreensState,
    modifier: Modifier = Modifier,
) {
    val startDestination = remember { getNavigationEntry(state.screens) }

    val navBackStack = rememberNavBackStack(pagedNavigationConfiguration, startDestination)

    LaunchedEffect(state.screens) {
        val newNavigationEntry = getNavigationEntry(state.screens)
        if (newNavigationEntry == navBackStack.last()) return@LaunchedEffect

        navBackStack.add(newNavigationEntry)
        if (navBackStack.size > 1) {
            navBackStack.subList(0, navBackStack.size - 1).clear()
        }
    }

    NavDisplay(
        backStack = navBackStack,
        contentAlignment = Alignment.Center,
        onBack = {},
        modifier = modifier,
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(
                    removeViewModelStoreOnPop = { true }
                ),
            ),
        entryProvider = entryProvider {
            entry<Page0Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Page1Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Page2Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Page3Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Page4Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Page5Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Page6Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Page7Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Page8Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }

            entry<Page9Navigation> { navigation ->
                OnlineNavigation(
                    id = navigation.id,
                    runningAsOverlay = runningAsOverlay,
                    onSuccessLogin = onSuccessLogin,
                    onRedirectError = onRedirectError,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    )
}

private fun getNavigationEntry(
    screens: OnlineScreens,
): NavKey {
    val selectedScreen = screens.find { it.selected } ?: screens.first()
    return when (selectedScreen.position) {
        1 -> Page1Navigation(selectedScreen.id)
        2 -> Page2Navigation(selectedScreen.id)
        3 -> Page3Navigation(selectedScreen.id)
        4 -> Page4Navigation(selectedScreen.id)
        5 -> Page5Navigation(selectedScreen.id)
        6 -> Page6Navigation(selectedScreen.id)
        7 -> Page7Navigation(selectedScreen.id)
        8 -> Page8Navigation(selectedScreen.id)
        9 -> Page9Navigation(selectedScreen.id)
        else -> Page0Navigation(selectedScreen.id)
    }
}
