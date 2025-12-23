package com.posse.tanksrandomizer.common.paged_screens_navigation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.paged_screens_navigation.compose.components.PagedScreensContent
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.PagedScreensViewModel
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreensAction
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreensEvent
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun PagedScreens(
    pagedScreen: @Composable (screenId: String) -> Unit,
    viewModel: PagedScreensViewModel,
    modifier: Modifier,
) {
    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    var selectedTab by remember(state) {
        mutableIntStateOf(
            state.screens
                .find { screenData -> screenData.metadata.selected }
                ?.metadata
                ?.position
                ?: 0
        )
    }

    val scope = rememberCoroutineScope()
    var animationJob: Job? by remember { mutableStateOf(null) }

    LaunchedEffect(animationJob) {
        viewModel.obtainEvent(PagedScreensEvent.ClearAction)
    }

    LaunchedEffect(action) {
        action?.let { pagedScreenAction ->
            when (pagedScreenAction) {
                is PagedScreensAction.CantAddScreens -> {
                    if (animationJob?.isActive == true) {
                        viewModel.obtainEvent(PagedScreensEvent.ClearAction)
                        return@let
                    }

                    @Suppress("AssignedValueIsNeverRead")
                    animationJob = scope.launch {
                        selectedTab = state.screens.size
                        delay(0.5.seconds)
                        selectedTab = pagedScreenAction.emptyScreenPosition
                    }
                }
            }

            viewModel.obtainEvent(PagedScreensEvent.ClearAction)
        }
    }

    PagedScreensContent(
        state = state,
        selectedTab = selectedTab,
        onEvent = viewModel::obtainEvent,
        pagedScreen = pagedScreen,
        modifier = modifier.clipToBounds(),
    )
}
