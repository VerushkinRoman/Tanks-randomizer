package com.posse.tanksrandomizer.feature_webview_screen.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import com.posse.tanksrandomizer.common.compose.base_components.LoadingIndicator
import com.posse.tanksrandomizer.common.data.networking.EndpointConstants.REDIRECT_URL
import com.posse.tanksrandomizer.feature_webview_screen.compose.components.ActionButtonsRow
import com.posse.tanksrandomizer.feature_webview_screen.compose.components.PlatformWebView
import com.posse.tanksrandomizer.feature_webview_screen.compose.model.NavigationAction.Exit
import com.posse.tanksrandomizer.feature_webview_screen.compose.model.NavigationAction.GoBack
import com.posse.tanksrandomizer.feature_webview_screen.compose.model.NavigationAction.GoForward
import com.posse.tanksrandomizer.feature_webview_screen.compose.model.NavigationAction.Reload
import com.posse.tanksrandomizer.feature_webview_screen.compose.model.NavigationAction.StopLoading

@Composable
fun WebViewScreen(
    url: String,
    runningAsOverlay: Boolean,
    onResult: (url: String) -> Unit,
    goBack: () -> Unit,
    modifier: Modifier,
) {
    val state = rememberWebViewState(url)
    val navigator = rememberWebViewNavigator()
    var showLoadingIndicator by remember { mutableStateOf(false) }

    val loadingProgress by remember {
        derivedStateOf { (state.loadingState as? LoadingState.Loading)?.progress ?: 0f }
    }

    LaunchedEffect(state.lastLoadedUrl, state.loadingState) {
        state.lastLoadedUrl?.let { url ->
            if (url.startsWith(REDIRECT_URL)) {
                showLoadingIndicator = true
                navigator.stopLoading()
                onResult(url)
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .safeDrawingPadding(),
    ) {
        LinearProgressIndicator(
            progress = { loadingProgress },
            color = MaterialTheme.colorScheme.onPrimary,
            trackColor = MaterialTheme.colorScheme.primary,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            modifier = Modifier.fillMaxWidth(),
        )

        ActionButtonsRow(
            additionalPadding = runningAsOverlay,
            onAction = { navigationAction ->
                when (navigationAction) {
                    Reload -> navigator.reload()
                    StopLoading -> navigator.stopLoading()
                    GoBack -> navigator.navigateBack()
                    GoForward -> navigator.navigateForward()
                    Exit -> goBack()
                }
            },
            forwardEnabled = navigator.canGoForward,
            backEnabled = navigator.canGoBack,
            reloadEnabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)
                )
                .padding(horizontal = 8.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            if (showLoadingIndicator) {
                LoadingIndicator()
            } else {
                PlatformWebView(
                    state = state,
                    navigator = navigator,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
