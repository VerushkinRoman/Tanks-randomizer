package com.posse.tanksrandomizer.feature_online_navigation.navigation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_online_navigation.navigation.compose.components.PagedOnlineScreensContent
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.PagedOnlineScreensViewModel
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensAction
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.compose.viewmodel.rememberViewModel
import kotlin.time.Duration.Companion.seconds

@Composable
fun PagedOnlineScreens(
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    modifier: Modifier,
) {
    val viewModel: PagedOnlineScreensViewModel by rememberViewModel()
    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    var selectedTab by remember(state) {
        mutableIntStateOf(
            state.screens.find { screenData -> screenData.selected }?.position ?: 0
        )
    }

    val scope = rememberCoroutineScope()
    var animationJob: Job? by remember { mutableStateOf(null) }

    LaunchedEffect(animationJob) {
        viewModel.obtainEvent(PagedOnlineScreensEvent.ClearAction)
    }

    LaunchedEffect(action) {
        action?.let { pagedOnlineScreenAction ->
            when (pagedOnlineScreenAction) {
                is PagedOnlineScreensAction.CantAddScreens -> {
                    if (animationJob?.isActive == true) {
                        viewModel.obtainEvent(PagedOnlineScreensEvent.ClearAction)
                        return@let
                    }

                    @Suppress("AssignedValueIsNeverRead")
                    animationJob = scope.launch {
                        selectedTab = state.screens.size
                        delay(0.5.seconds)
                        selectedTab = pagedOnlineScreenAction.emptyScreenPosition
                    }
                }
            }

            viewModel.obtainEvent(PagedOnlineScreensEvent.ClearAction)
        }
    }

    PagedOnlineScreensContent(
        runningAsOverlay = runningAsOverlay,
        onRedirectError = onRedirectError,
        state = state,
        selectedTab = selectedTab,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}
