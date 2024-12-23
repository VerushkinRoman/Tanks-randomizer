package com.posse.tanksrandomizer.feature_settings_screen.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.common.presentation.utils.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.SettingsScreenContent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.SettingsViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent

@Composable
fun SettingsScreen(
    showRotation: Boolean,
    showFloatingButtonSettings: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val viewModel = viewModel { SettingsViewModel() }

    val state by viewModel.viewStates().collectAsStateWithLifecycle()
    val action by viewModel.viewActions().collectAsStateWithLifecycle()

    LaunchedEffect(action) {
        when (action) {
            SettingsAction.GoToAppSettings -> context.openSettings()
            else -> Unit
        }
        viewModel.obtainEvent(SettingsEvent.ClearAction)
    }

    SettingsScreenContent(
        viewState = state,
        showRotation = showRotation,
        showFloatingButtonSettings = showFloatingButtonSettings,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}

private fun Context.openSettings() {
    Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        Uri.parse("package:${packageName}")
    )
        .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
        .also { startActivity(it) }
}