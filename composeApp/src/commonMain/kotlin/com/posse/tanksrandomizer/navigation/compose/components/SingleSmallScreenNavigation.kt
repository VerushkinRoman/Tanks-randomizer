package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.NoAccounts
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.compose.rememberNavController
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.compose.utils.ErrorHandler.getRedirectErrorMessage
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.ResponseStatus
import com.posse.tanksrandomizer.navigation.compose.components.NavigationItem.Offline
import com.posse.tanksrandomizer.navigation.compose.components.NavigationItem.Online
import com.posse.tanksrandomizer.navigation.compose.components.NavigationItem.Settings
import com.posse.tanksrandomizer.navigation.domain.repository.NavigationRepository
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OnlineNavigationRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.SettingsScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.toRoute
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.lesta_logo
import tanks_randomizer.composeapp.generated.resources.lesta_logo_outline

@Composable
fun SingleSmallScreenNavigation(
    showRotation: Boolean,
    runningAsOverlay: Boolean,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
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

    Column(
        modifier = modifier
    ) {
        SingleScreenNavigationHost(
            navController = navController,
            startDestination = startDestination,
            showRotation = showRotation,
            runningAsOverlay = runningAsOverlay,
            onRedirectError = { error ->
                if (error.status == ResponseStatus.ERROR.value) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = getRedirectErrorMessage(error)
                        )
                    }
                }
            },
            modifier = Modifier.weight(1f),
        )

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
        ) {
            NavigationItem.entries.forEach { item ->
                val selected = item.screenRoute == currentDestination

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.screenRoute) {
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onBackground,
                        selectedTextColor = MaterialTheme.colorScheme.onBackground,
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    label = {
                        RandomizerText(
                            text = stringResource(item.label),
                            color = LocalContentColor.current,
                        )
                    },
                    icon = {
                        Image(
                            painter = when (item) {
                                Online -> painterResource(
                                    if (selected) Res.drawable.lesta_logo else Res.drawable.lesta_logo_outline
                                )

                                Offline -> rememberVectorPainter(
                                    if (selected) Icons.Filled.NoAccounts else Icons.Outlined.NoAccounts
                                )

                                Settings -> rememberVectorPainter(
                                    if (selected) Icons.Filled.Settings else Icons.Outlined.Settings
                                )
                            },
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                            colorFilter = ColorFilter.tint(LocalContentColor.current),
                            modifier = Modifier.height(ButtonDefaults.MinHeight * 3 / 4)
                        )
                    },
                )
            }
        }
    }
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
