package com.posse.tanksrandomizer.feature_settings_screen.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.SettingsScreenContent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.SettingsViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent

@Composable
fun SettingsScreen(
    showRotation: Boolean,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel { SettingsViewModel() }

    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    LaunchedEffect(action) {
        when (action) {
            SettingsAction.GoToAppSettings -> getOverlayPermission(Inject.instance())
            else -> Unit
        }
        viewModel.obtainEvent(SettingsEvent.ClearAction)
    }

    SettingsScreenContent(
        viewState = state,
        showRotation = showRotation,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}

internal expect fun getOverlayPermission(platformConfiguration: PlatformConfiguration)