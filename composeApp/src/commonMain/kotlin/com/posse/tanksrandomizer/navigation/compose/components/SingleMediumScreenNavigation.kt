package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute
import org.jetbrains.compose.resources.stringResource

@Composable
fun SingleMediumScreenNavigation(
    snackbarHostState: SnackbarHostState,
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier,
) {
    SingleScreenNavigation(
        snackbarHostState = snackbarHostState,
    ) { navController, onRedirectError, startDestination, currentDestination ->
        Row(
            modifier = modifier,
        ) {
            NavRail(
                navController = navController,
                currentDestination = currentDestination,
                modifier = Modifier.fillMaxHeight(),
            )

            SingleScreenNavigationHost(
                navController = navController,
                startDestination = startDestination,
                runningAsOverlay = runningAsOverlay,
                onRedirectError = onRedirectError,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun NavRail(
    navController: NavHostController,
    currentDestination: ScreenRoute,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier,
    ) {
        Spacer(Modifier.weight(1f))

        NavigationItem.entries.forEach { item ->
            val selected = item.screenRoute == currentDestination

            NavigationRailItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        launchSingleTop = true
                    }
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                label = {
                    RandomizerText(
                        text = stringResource(item.label),
                        color = LocalContentColor.current,
                    )
                },
                icon = {
                    NavigationIcon(
                        item = item,
                        selected = selected,
                    )
                },
            )
        }

        Spacer(Modifier.weight(1f))
    }
}
