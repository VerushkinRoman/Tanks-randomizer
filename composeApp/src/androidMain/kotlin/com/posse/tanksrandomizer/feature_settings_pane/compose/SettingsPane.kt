package com.posse.tanksrandomizer.feature_settings_pane.compose

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_settings_pane.compose.components.SettingsPaneContent
import com.posse.tanksrandomizer.feature_settings_pane.presentation.SettingsViewModel
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsEvent

@Composable
fun SettingsPane(
    showRotation: Boolean,
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val viewModel = viewModel { SettingsViewModel() }

    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    LaunchedEffect(action) {
        action?.let {
            when (it) {
                SettingsAction.GoToAppSettings -> context.openSettings()
            }
            viewModel.obtainEvent(SettingsEvent.ClearAction)
        }
    }

    SettingsPaneContent(
        viewState = state,
        showRotation = showRotation,
        runningAsOverlay = runningAsOverlay,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}

private fun Context.openSettings() {
    Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        "package:${packageName}".toUri()
    )
        .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
        .also { startActivity(it) }
}
