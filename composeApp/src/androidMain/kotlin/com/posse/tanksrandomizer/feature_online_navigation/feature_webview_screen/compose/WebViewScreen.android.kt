package com.posse.tanksrandomizer.feature_online_navigation.feature_webview_screen.compose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun WebViewBackHandler(onBack: () -> Unit) = BackHandler(onBack = onBack)
