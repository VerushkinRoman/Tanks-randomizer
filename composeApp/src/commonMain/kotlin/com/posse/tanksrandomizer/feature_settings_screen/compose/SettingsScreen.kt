package com.posse.tanksrandomizer.feature_settings_screen.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.SettingsScreenContent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.SettingsScreenViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import io.github.sudarshanmhasrup.localina.api.LocaleUpdater
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.viewmodel.rememberViewModel
import kotlin.getValue

@Composable
fun SettingsScreen(
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier,
) {
    val viewModel: SettingsScreenViewModel by rememberViewModel()
    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()
    val platform: PlatformConfiguration by rememberInstance()

    LaunchedEffect(action) {
        action?.let {
            when (it) {
                SettingsAction.GoToAppSettings -> openSettings(platform)
                is SettingsAction.UpdateLocale -> {
                    LocaleUpdater.updateLocale(it.locale.name.lowercase())
                }
                is SettingsAction.FullScreenChanged -> changeMode(platform, it.enabled)
            }
            viewModel.obtainEvent(SettingsEvent.ClearAction)
        }
    }

    SettingsScreenContent(
        viewState = state,
        runningAsOverlay = runningAsOverlay,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}

expect fun openSettings(platform: PlatformConfiguration)
expect fun changeMode(platform: PlatformConfiguration, fullScreen: Boolean)
