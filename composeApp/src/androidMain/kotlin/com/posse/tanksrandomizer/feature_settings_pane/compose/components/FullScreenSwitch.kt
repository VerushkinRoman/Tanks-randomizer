package com.posse.tanksrandomizer.feature_settings_pane.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloseFullscreen
import androidx.compose.material.icons.rounded.OpenInFull
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.common.compose.theme.themedSegmentedButtonColors
import com.posse.tanksrandomizer.feature_settings_pane.compose.components.common.CommonSettingsActionBlock
import com.posse.tanksrandomizer.feature_settings_pane.compose.components.common.CommonSettingsActionTitle
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsEvent
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.settings_mode_desc
import tanks_randomizer.composeapp.generated.resources.settings_mode_fullscreen
import tanks_randomizer.composeapp.generated.resources.settings_mode_title
import tanks_randomizer.composeapp.generated.resources.settings_mode_window

@Composable
fun FullScreenSwitch(
    enabled: Boolean,
    fullScreenModeEnabled: Boolean,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    CommonSettingsActionBlock(
        caption = { centered ->
            ScreenSwitchDescription(
                centered = centered,
                fullScreenModeEnabled = fullScreenModeEnabled,
            )
        },
        action = {
            ScreenSwitchControlButtons(
                fullScreenModeEnabled = fullScreenModeEnabled,
                enabled = enabled,
                onFullScreenClick = { onEvent(SettingsEvent.FullScreenModeChanged(fullScreen = true)) },
                onWindowClick = { onEvent(SettingsEvent.FullScreenModeChanged(fullScreen = false)) },
            )
        },
        modifier = modifier
    )
}

@Composable
private fun ScreenSwitchDescription(
    centered: Boolean,
    fullScreenModeEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val descriptionText = stringResource(Res.string.settings_mode_desc) +
            " " +
            stringResource(
                if (fullScreenModeEnabled) Res.string.settings_mode_fullscreen
                else Res.string.settings_mode_window
            )

    CommonSettingsActionTitle(
        centered = centered,
        title = stringResource(Res.string.settings_mode_title),
        subtitle = descriptionText,
        modifier = modifier,
    )
}

@Composable
private fun ScreenSwitchControlButtons(
    fullScreenModeEnabled: Boolean,
    enabled: Boolean,
    onFullScreenClick: () -> Unit,
    onWindowClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier,
    ) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 0,
                count = 2,
            ),
            onClick = onFullScreenClick,
            selected = fullScreenModeEnabled,
            enabled = enabled,
            colors = themedSegmentedButtonColors(),
            label = {
                Icon(
                    imageVector = Icons.Rounded.OpenInFull,
                    contentDescription = stringResource(Res.string.settings_mode_fullscreen),
                )
            },
        )

        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 2,
            ),
            onClick = onWindowClick,
            selected = !fullScreenModeEnabled,
            enabled = enabled,
            colors = themedSegmentedButtonColors(),
            label = {
                Icon(
                    imageVector = Icons.Rounded.CloseFullscreen,
                    contentDescription = stringResource(Res.string.settings_mode_window),
                )
            },
        )
    }
}
