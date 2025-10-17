package com.posse.tanksrandomizer.feature_settings_pane.compose.components

import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsState

@Composable
fun SettingsPaneContent(
    viewState: SettingsState,
    showRotation: Boolean,
    runningAsOverlay: Boolean,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var canDrawOverlay by remember { mutableStateOf(Settings.canDrawOverlays(context)) }

    LifecycleResumeEffect(true) {
        canDrawOverlay = Settings.canDrawOverlays(context)
        onPauseOrDispose {}
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier,
    ) {
        if (showRotation) {
            RotationBlock(
                screenRotation = viewState.screenRotation,
                onEvent = onEvent,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        if (runningAsOverlay) {
            FloatingButtonConfig(
                state = viewState,
                onEvent = onEvent,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        if (!canDrawOverlay) {
            OverlayPermissionControl(
                openOverlaySettings = { onEvent(SettingsEvent.OverlayButtonPressed) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        FullScreenSwitch(
            enabled = canDrawOverlay,
            fullScreenModeEnabled = viewState.fullScreenMode,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (canDrawOverlay) 1f else 0.3f),
        )
    }
}
