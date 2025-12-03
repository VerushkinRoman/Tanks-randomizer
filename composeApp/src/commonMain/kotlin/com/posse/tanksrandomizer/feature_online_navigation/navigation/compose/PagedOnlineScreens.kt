package com.posse.tanksrandomizer.feature_online_navigation.navigation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_online_navigation.navigation.compose.components.PagedOnlineScreensContent
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.PagedOnlineScreensViewModel
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensEvent
import org.kodein.di.compose.viewmodel.rememberViewModel

@Composable
fun PagedOnlineScreens(
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    modifier: Modifier,
) {
    val viewModel: PagedOnlineScreensViewModel by rememberViewModel()
    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    LaunchedEffect(action) {
        action?.let { pagedOnlineScreenAction ->
            when (pagedOnlineScreenAction) {
                else -> Unit
            }

            viewModel.obtainEvent(PagedOnlineScreensEvent.ClearAction)
        }
    }

    PagedOnlineScreensContent(
        runningAsOverlay = runningAsOverlay,
        onRedirectError = onRedirectError,
        state = state,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}
