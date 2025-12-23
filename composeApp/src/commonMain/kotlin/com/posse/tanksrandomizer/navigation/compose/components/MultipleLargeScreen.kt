package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.elementSize
import com.posse.tanksrandomizer.common.compose.utils.screenSize
import com.posse.tanksrandomizer.common.paged_screens_navigation.compose.PagedScreens
import com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.PagedOfflineScreensViewModel
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.PagedOnlineScreensViewModel
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensEvent
import com.posse.tanksrandomizer.feature_settings_screen.compose.SettingsScreen
import com.posse.tanksrandomizer.navigation.compose.models.LoginResult
import kotlinx.coroutines.flow.SharedFlow
import org.kodein.di.compose.viewmodel.rememberViewModel

@Composable
internal fun MultipleLargeScreen(
    runningAsOverlay: Boolean,
    pagedOnlineScreen: @Composable (screenId: String) -> Unit,
    pagedOfflineScreen: @Composable (screenId: String) -> Unit,
    loginResultFlow: SharedFlow<LoginResult>,
    modifier: Modifier = Modifier,
) {
    val verticalDivider: @Composable () -> Unit = {
        VerticalDivider(
            thickness = BorderWidth,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 16.dp),
        )
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        OnlineScreenPane(
            pagedOnlineScreen = pagedOnlineScreen,
            loginResultFlow = loginResultFlow,
            modifier = Modifier.weight(1f),
        )

        verticalDivider()

        OfflineScreenPane(
            pagedOfflineScreen = pagedOfflineScreen,
            modifier = Modifier.weight(1f),
        )

        verticalDivider()

        SettingsScreenPane(
            runningAsOverlay = runningAsOverlay,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun OnlineScreenPane(
    pagedOnlineScreen: @Composable (screenId: String) -> Unit,
    loginResultFlow: SharedFlow<LoginResult>,
    modifier: Modifier = Modifier
) {
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

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        CompositionLocalProvider(
            LocalElementSize provides elementSize(maxWidth),
            LocalSizeClass provides screenSize(maxWidth, maxHeight),
        ) {
            PagedScreens(
                pagedScreen = pagedOnlineScreen,
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun OfflineScreenPane(
    pagedOfflineScreen: @Composable (screenId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: PagedOfflineScreensViewModel by rememberViewModel()

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        CompositionLocalProvider(
            LocalElementSize provides elementSize(maxWidth),
            LocalSizeClass provides screenSize(maxWidth, maxHeight),
        ) {
            PagedScreens(
                pagedScreen = pagedOfflineScreen,
                viewModel = viewModel,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun SettingsScreenPane(
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        CompositionLocalProvider(
            LocalElementSize provides elementSize(maxWidth),
            LocalSizeClass provides screenSize(maxWidth, maxHeight),
        ) {
            SettingsScreen(
                runningAsOverlay = runningAsOverlay,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
