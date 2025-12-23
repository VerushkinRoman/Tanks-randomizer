package com.posse.tanksrandomizer.common.paged_screens_navigation.compose.components

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
import com.posse.tanksrandomizer.common.compose.components.getNavAnimation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreensState
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page0Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page1Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page2Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page3Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page4Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page5Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page6Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page7Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page8Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.Page9Navigation
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens.pagedNavigationConfiguration

@Composable
internal fun PagedNavigation(
    state: PagedScreensState,
    selectedOrder: Int,
    previousSelectedOrder: Int,
    pagedScreen: @Composable (screenId: String) -> Unit,
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
        transitionSpec = getNavAnimation(
            selectedOrder = selectedOrder,
            previousSelectedOrder = previousSelectedOrder,
        ),
        entryProvider = entryProvider {
            entry<Page0Navigation> { navigation ->
                pagedScreen(
                    navigation.id,
                )
            }

            entry<Page1Navigation> { navigation ->
                pagedScreen(navigation.id)
            }

            entry<Page2Navigation> { navigation ->
                pagedScreen(navigation.id)
            }

            entry<Page3Navigation> { navigation ->
                pagedScreen(navigation.id)
            }

            entry<Page4Navigation> { navigation ->
                pagedScreen(navigation.id)
            }

            entry<Page5Navigation> { navigation ->
                pagedScreen(navigation.id)
            }

            entry<Page6Navigation> { navigation ->
                pagedScreen(navigation.id)
            }

            entry<Page7Navigation> { navigation ->
                pagedScreen(navigation.id)
            }

            entry<Page8Navigation> { navigation ->
                pagedScreen(navigation.id)
            }

            entry<Page9Navigation> { navigation ->
                pagedScreen(navigation.id)
            }
        }
    )
}

private fun getNavigationEntry(
    screens: List<PagedScreen<*>>,
): NavKey {
    val selectedScreen = screens.find { it.metadata.selected } ?: screens.first()
    return when (selectedScreen.metadata.position) {
        1 -> Page1Navigation(selectedScreen.metadata.id)
        2 -> Page2Navigation(selectedScreen.metadata.id)
        3 -> Page3Navigation(selectedScreen.metadata.id)
        4 -> Page4Navigation(selectedScreen.metadata.id)
        5 -> Page5Navigation(selectedScreen.metadata.id)
        6 -> Page6Navigation(selectedScreen.metadata.id)
        7 -> Page7Navigation(selectedScreen.metadata.id)
        8 -> Page8Navigation(selectedScreen.metadata.id)
        9 -> Page9Navigation(selectedScreen.metadata.id)
        else -> Page0Navigation(selectedScreen.metadata.id)
    }
}
