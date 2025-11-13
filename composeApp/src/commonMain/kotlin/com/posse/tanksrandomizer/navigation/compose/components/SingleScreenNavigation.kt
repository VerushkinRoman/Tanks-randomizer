package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.posse.tanksrandomizer.common.compose.utils.ErrorHandler.getRedirectErrorMessage
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.ResponseStatus
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.navigation.domain.repository.NavigationRepository
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OnlineNavigationRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.SettingsScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.toRoute
import kotlinx.coroutines.launch

@Composable
fun SingleScreenNavigation(
    snackbarHostState: SnackbarHostState,
    content: @Composable (
        navController: NavHostController,
        onRedirectError: (ErrorResponse) -> Unit,
        startDestination: ScreenRoute,
        currentDestination: ScreenRoute,
    ) -> Unit,
) {
    val navigationRepository: NavigationRepository = remember { Inject.instance() }
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val startDestination =
        remember(navigationRepository) { getStartDestination(navigationRepository) }

    var currentDestination by remember {
        mutableStateOf(startDestination)
    }

    LaunchedEffect(true) {
        navController.currentBackStackEntryFlow.collect { value ->
            currentDestination = when (value.destination.route?.toRoute()) {
                OnlineNavigationRoute.toRoute() -> OnlineNavigationRoute
                OfflineScreenRoute.toRoute() -> OfflineScreenRoute
                SettingsScreenRoute.toRoute() -> SettingsScreenRoute
                else -> startDestination
            }

            navigationRepository.setCurrentScreenRoute(currentDestination.toRoute())
        }
    }

    content(
        navController,
        { error ->
            if (error.status == ResponseStatus.ERROR.value) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = getRedirectErrorMessage(error)
                    )
                }
            }
        },
        startDestination,
        currentDestination,
    )
}

private fun getStartDestination(
    navigationRepository: NavigationRepository,
): ScreenRoute {
    return when (navigationRepository.getScreenRoute()) {
        OnlineNavigationRoute.toRoute() -> OnlineNavigationRoute
        OfflineScreenRoute.toRoute() -> OfflineScreenRoute
        SettingsScreenRoute.toRoute() -> SettingsScreenRoute
        else -> OnlineNavigationRoute
    }
}

private fun String.toRoute(): String {
    return substringAfterLast('.').takeLastWhile { it != '/' }
}
