package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.posse.tanksrandomizer.navigation.domain.repository.NavigationRepository
import com.posse.tanksrandomizer.navigation.presentation.screens.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.OnlineNavigationRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.SettingsScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.mainNavigationConfig
import com.posse.tanksrandomizer.navigation.presentation.screens.toRoute
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.rememberInstance

@Composable
fun SingleScreenNavigation(
    runningAsOverlay: Boolean,
    pagedOnlineScreen: @Composable (screenId: String) -> Unit,
    pagedOfflineScreen: @Composable (screenId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navigationRepository: NavigationRepository by rememberInstance()
    val startDestination = remember(navigationRepository) { navigationRepository.startDestination() }
    val navBackStack = rememberNavBackStack(mainNavigationConfig, startDestination)

    val portrait = when (LocalSizeClass.current) {
        ScreenSize.Small -> true
        else -> false
    }

    var selectedItem by remember {
        mutableIntStateOf(
            when (startDestination) {
                is OnlineNavigationRoute -> 0
                is OfflineScreenRoute -> 1
                is SettingsScreenRoute -> 2
                else -> 0
            }
        )
    }

    var previousSelectedItem by remember { mutableIntStateOf(selectedItem) }

    val onItemSelected: (Int) -> Unit = { index ->
        previousSelectedItem = selectedItem
        selectedItem = index
    }

    val navHost: @Composable (Modifier) -> Unit = { modifier ->
        SingleScreenNavigationHost(
            navBackStack = navBackStack,
            runningAsOverlay = runningAsOverlay,
            portrait = portrait,
            selectedOrder = selectedItem,
            previousSelectedOrder = previousSelectedItem,
            pagedOnlineScreen = pagedOnlineScreen,
            pagedOfflineScreen = pagedOfflineScreen,
            modifier = modifier.clipToBounds(),
        )
    }

    if (portrait) {
        Column(
            modifier = modifier
        ) {
            navHost(Modifier.weight(1f))

            NavBar(
                navBackStack = navBackStack,
                repository = navigationRepository,
                onItemSelected = onItemSelected,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    } else {
        Row(
            modifier = modifier,
        ) {
            NavRail(
                navBackStack = navBackStack,
                repository = navigationRepository,
                onItemSelected = onItemSelected,
                modifier = Modifier.fillMaxHeight(),
            )

            navHost(Modifier.weight(1f))
        }
    }
}

@Composable
private fun NavRail(
    navBackStack: NavBackStack<NavKey>,
    repository: NavigationRepository,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier,
    ) {
        Spacer(Modifier.weight(1f))

        NavigationItem.entries.forEachIndexed { index, item ->
            val selected = item.screenRoute == navBackStack.lastOrNull()

            NavigationRailItem(
                selected = selected,
                onClick = {
                    onItemSelected(index)
                    navBackStack.navigateToRoute(
                        route = item.screenRoute,
                        navigationRepository = repository
                    )
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

@Composable
private fun NavBar(
    navBackStack: NavBackStack<NavKey>,
    repository: NavigationRepository,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier,
    ) {
        NavigationItem.entries.forEachIndexed { index, item ->
            val selected = item.screenRoute == navBackStack.lastOrNull()

            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemSelected(index)
                    navBackStack.navigateToRoute(
                        route = item.screenRoute,
                        navigationRepository = repository
                    )
                },
                colors = NavigationBarItemDefaults.colors(
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
    }
}

private fun NavBackStack<NavKey>.navigateToRoute(
    route: NavKey,
    navigationRepository: NavigationRepository
) {
    if (lastOrNull() == route) return

    add(route)
    if (size > 1) {
        subList(0, size - 1).clear()
    }
    navigationRepository.setCurrentScreenRoute(route.toRoute())
}

private fun NavigationRepository.startDestination(): NavKey {
    return when (getScreenRoute()) {
        OnlineNavigationRoute.toRoute() -> OnlineNavigationRoute
        OfflineScreenRoute.toRoute() -> OfflineScreenRoute
        SettingsScreenRoute.toRoute() -> SettingsScreenRoute
        else -> OnlineNavigationRoute
    }
}
