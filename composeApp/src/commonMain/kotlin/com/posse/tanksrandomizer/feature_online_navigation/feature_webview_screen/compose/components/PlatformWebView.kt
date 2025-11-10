package com.posse.tanksrandomizer.feature_online_navigation.feature_webview_screen.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.web.PlatformWebViewParams
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewNavigator

@Composable
expect fun PlatformWebView(
    state: WebViewState,
    modifier: Modifier = Modifier,
    captureBackPresses: Boolean = true,
    navigator: WebViewNavigator = rememberWebViewNavigator(),
    webViewJsBridge: WebViewJsBridge? = null,
    onCreated: () -> Unit = {},
    onDispose: () -> Unit = {},
    platformWebViewParams: PlatformWebViewParams? = null,
)

@Composable
fun CommonWebView(
    state: WebViewState,
    navigator: WebViewNavigator = rememberWebViewNavigator(),
    modifier: Modifier = Modifier,
) = WebView(
    state = state,
    navigator = navigator,
    modifier = modifier
)
