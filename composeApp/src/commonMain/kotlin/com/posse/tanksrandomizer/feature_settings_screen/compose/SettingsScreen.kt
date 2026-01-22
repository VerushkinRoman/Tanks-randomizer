package com.posse.tanksrandomizer.feature_settings_screen.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.SettingsScreenContent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.SettingsScreenViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import io.github.sudarshanmhasrup.localina.api.LocaleUpdater
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.viewmodel.rememberViewModel
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.ad_disabled
import tanks_randomizer.composeapp.generated.resources.ad_enabled

@Composable
fun SettingsScreen(
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier,
) {
    val viewModel: SettingsScreenViewModel by rememberViewModel()
    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()
    val platform: PlatformConfiguration by rememberInstance()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(action) {
        action?.let {
            when (it) {
                SettingsAction.GoToAppSettings -> openSettings(platform)
                is SettingsAction.UpdateLocale -> {
                    LocaleUpdater.updateLocale(it.locale.name.lowercase())
                }
                is SettingsAction.FullScreenChanged -> changeMode(platform, it.enabled)
                is SettingsAction.AdChanged -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = getString(
                                if (it.enabled) Res.string.ad_enabled
                                else Res.string.ad_disabled
                            )
                        )
                    }
                }
            }
            viewModel.obtainEvent(SettingsEvent.ClearAction)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent,
        modifier = Modifier.fillMaxSize(),
    ) {
        SettingsScreenContent(
            viewState = state,
            runningAsOverlay = runningAsOverlay,
            onEvent = viewModel::obtainEvent,
            modifier = modifier
        )
    }
}

expect fun openSettings(platform: PlatformConfiguration)
expect fun changeMode(platform: PlatformConfiguration, fullScreen: Boolean)
