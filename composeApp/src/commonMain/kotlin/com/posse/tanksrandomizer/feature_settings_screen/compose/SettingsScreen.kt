package com.posse.tanksrandomizer.feature_settings_screen.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.presentation.utils.getPlatformFactory
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.SettingsScreenContent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.SettingsScreenViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import io.github.sudarshanmhasrup.localina.api.LocaleUpdater

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = viewModel(factory = getPlatformFactory()),
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    LaunchedEffect(action) {
        action?.let {
            when (it) {
                SettingsAction.GoToAppSettings -> openSettings()
                is SettingsAction.UpdateLocale -> {
                    LocaleUpdater.updateLocale(it.locale.name.lowercase())
                }
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

expect fun openSettings()
