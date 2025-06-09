package com.posse.tanksrandomizer.feature_settings_pane.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsState

@Composable
fun SettingsPaneContent(
    viewState: SettingsState,
    showRotation: Boolean,
    showFloatingButtonSettings: Boolean,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(6.dp)
    ) {
        if (showRotation) {
            RotationBlock(
                rotation = viewState.rotation,
                onEvent = onEvent,
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        if (showFloatingButtonSettings) {
            FloatingButtonConfig(
                state = viewState,
                onEvent = onEvent,
                modifier = Modifier.padding(WindowInsets.safeGestures.asPaddingValues())
            )
        }

        FullScreenSwitch(
            autoRotation = viewState.fullScreenMode,
            onClick = { onEvent(SettingsEvent.FullScreenModePressed) },
            onAppSettingsClick = { onEvent(SettingsEvent.AppSettingsPressed) }
        )
    }
}
