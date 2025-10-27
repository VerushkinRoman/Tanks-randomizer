package com.posse.tanksrandomizer.feature_webview_screen.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.web.PlatformWebViewParams
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState

@Composable
actual fun PlatformWebView(
    state: WebViewState,
    modifier: Modifier,
    captureBackPresses: Boolean,
    navigator: WebViewNavigator,
    webViewJsBridge: WebViewJsBridge?,
    onCreated: () -> Unit,
    onDispose: () -> Unit,
    platformWebViewParams: PlatformWebViewParams?
) = CommonWebView(
    state = state,
    navigator = navigator,
    modifier = modifier
)
