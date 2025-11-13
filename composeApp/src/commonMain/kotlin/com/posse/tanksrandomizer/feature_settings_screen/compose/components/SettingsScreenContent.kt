package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.CommonScreenColumn
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsState

@Composable
fun SettingsScreenContent(
    viewState: SettingsState,
    runningAsOverlay: Boolean,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val columnSpacer = 16.dp

    val divider: @Composable (ColumnScope.() -> Unit) = {
        Spacer(Modifier.height(columnSpacer))

        HorizontalDivider(
            thickness = BorderWidth,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(columnSpacer))
    }

    CommonScreenColumn(
        runningAsOverlay = runningAsOverlay,
        modifier = modifier
    ) {
        LocaleBlock(
            currentLocale = viewState.locale,
            onLocaleChange = { onEvent(SettingsEvent.ChangeLocale(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        if (rotationAvailable) {
            divider()

            RotationBlock(
                screenRotation = viewState.screenRotation,
                enabled = !runningAsOverlay,
                onEvent = onEvent,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        if (overlayAvailable) {
            divider()

            OverlayPermissionControl(
                openOverlaySettings = { onEvent(SettingsEvent.OverlayButtonPressed) },
                modifier = Modifier.fillMaxWidth()
            )

            divider()

            FullScreenSwitch(
                fullScreenModeEnabled = viewState.fullScreenMode,
                onEvent = onEvent,
                modifier = Modifier.fillMaxWidth(),
            )

            divider()

            FloatingButtonConfig(
                state = viewState,
                onEvent = onEvent,
                columnSpacer = columnSpacer,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

expect val rotationAvailable: Boolean

expect val overlayAvailable: Boolean
