package com.posse.tanksrandomizer.navigation.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.ScreenFrameShape
import com.posse.tanksrandomizer.common.compose.utils.ErrorHandler.getRedirectErrorMessage
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.posse.tanksrandomizer.common.domain.models.ResponseStatus
import com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.compose.OfflineScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.compose.OnlineNavigation
import com.posse.tanksrandomizer.navigation.compose.components.MultipleLargeScreen
import com.posse.tanksrandomizer.navigation.compose.components.SingleScreenNavigation
import com.posse.tanksrandomizer.navigation.compose.models.LoginResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun MainNavigation(
    runningAsOverlay: Boolean = false,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val loginResultFlow: MutableSharedFlow<LoginResult> = remember { MutableSharedFlow() }

    val pagedScreensModifier = Modifier
        .fillMaxSize()
        .padding(4.dp)
        .border(
            width = BorderWidth,
            color = MaterialTheme.colorScheme.primary,
            shape = ScreenFrameShape
        )
        .clip(ScreenFrameShape)

    val pagedOnlineScreen: @Composable (screenId: String) -> Unit = { screenId ->
        OnlineNavigation(
            screenId = screenId,
            runningAsOverlay = runningAsOverlay,
            onSuccessLogin = { id, name, token ->
                scope.launch {
                    loginResultFlow.emit(LoginResult(id, name, token))
                }
            },
            onRedirectError = { error ->
                if (error.status == ResponseStatus.ERROR.value) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = getRedirectErrorMessage(error)
                        )
                    }
                }
            },
            modifier = pagedScreensModifier,
        )
    }

    val pagedOfflineScreen: @Composable (screenId: String) -> Unit = { screenId ->
        OfflineScreen(
            screenId = screenId,
            runningAsOverlay = runningAsOverlay,
            modifier = pagedScreensModifier,
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        when (LocalSizeClass.current) {
            ScreenSize.Large -> MultipleLargeScreen(
                runningAsOverlay = runningAsOverlay,
                pagedOnlineScreen = pagedOnlineScreen,
                pagedOfflineScreen = pagedOfflineScreen,
                loginResultFlow = loginResultFlow,
                modifier = Modifier.fillMaxSize(),
            )

            else -> SingleScreenNavigation(
                runningAsOverlay = runningAsOverlay,
                pagedOnlineScreen = pagedOnlineScreen,
                pagedOfflineScreen = pagedOfflineScreen,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
