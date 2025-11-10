package com.posse.tanksrandomizer.feature_online_navigation.feature_webview_screen.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.web.PlatformWebViewParams
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.WebViewState
import com.posse.tanksrandomizer.common.compose.base_components.LoadingIndicator
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_online_navigation.feature_webview_screen.compose.util.KFCEStatus
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFAcknowledge
import dev.datlag.kcef.KCEFBuilder
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.webview_restart_required
import java.io.File

@OptIn(KCEFAcknowledge::class)
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
) {
    var restartRequired by remember { mutableStateOf(false) }
    var downloading by remember { mutableStateOf(0F) }
    var initialized by remember { mutableStateOf(KFCEStatus.initialized) }
    val download: KCEFBuilder.Download =
        remember { KCEFBuilder.Download.Builder().github().build() }
    val dispatchers = remember { Inject.instance<Dispatchers>() }

    LaunchedEffect(Unit) {
        withContext(dispatchers.io) {
            KCEF.init(builder = {
                installDir(File("kcef-bundle"))

                KCEFBuilder.Download.Builder().github {
                    release("jbr-release-17.0.10b1087.23")
                }.buffer(download.bufferSize).build()

                progress {
                    onDownloading {
                        downloading = maxOf(it, 0F)
                    }
                    onInitialized {
                        KFCEStatus.initialized = true
                        initialized = true
                    }
                }
                settings {
                    cachePath = File("cache").absolutePath
                }
            }, onError = {
                it?.printStackTrace() ?: println("Error")
            }, onRestartRequired = {
                restartRequired = true
            })
        }
    }

    if (restartRequired) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            RandomizerText(
                text = stringResource(Res.string.webview_restart_required),
            )
        }
    } else {
        if (initialized) {
            CommonWebView(
                state = state,
                navigator = navigator,
                modifier = modifier,
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                LinearProgressIndicator(
                    progress = { downloading / 100 },
                    color = MaterialTheme.colorScheme.onPrimary,
                    trackColor = MaterialTheme.colorScheme.primary,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth(),
                )

                LoadingIndicator()
            }
        }
    }
}
