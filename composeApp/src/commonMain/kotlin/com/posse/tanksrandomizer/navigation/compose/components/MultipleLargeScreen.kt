package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.utils.ErrorHandler.getRedirectErrorMessage
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.elementSize
import com.posse.tanksrandomizer.common.compose.utils.screenSize
import com.posse.tanksrandomizer.feature_offline_screen.compose.OfflineScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.compose.PagedOnlineScreens
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_settings_screen.compose.SettingsScreen
import kotlinx.coroutines.launch

@Composable
internal fun MultipleLargeScreen(
    snackbarHostState: SnackbarHostState,
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

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
            runningAsOverlay = runningAsOverlay,
            onRedirectError = { error ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = getRedirectErrorMessage(error)
                    )
                }
            },
            modifier = Modifier.weight(1f),
        )

        verticalDivider()

        OfflineScreenPane(
            runningAsOverlay = runningAsOverlay,
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
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
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
            PagedOnlineScreens(
                runningAsOverlay = runningAsOverlay,
                onRedirectError = onRedirectError,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun OfflineScreenPane(
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
            OfflineScreen(
                runningAsOverlay = runningAsOverlay,
                modifier = Modifier.fillMaxSize()
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
