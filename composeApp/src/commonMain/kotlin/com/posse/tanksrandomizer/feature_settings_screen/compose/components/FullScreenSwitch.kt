package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloseFullscreen
import androidx.compose.material.icons.rounded.OpenInFull
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.posse.tanksrandomizer.common.compose.theme.themedSegmentedButtonColors
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.common.CommonSettingsActionBlock
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.common.CommonSettingsActionTitle
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.rememberInstance
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.settings_mode_desc
import tanks_randomizer.composeapp.generated.resources.settings_mode_fullscreen
import tanks_randomizer.composeapp.generated.resources.settings_mode_title
import tanks_randomizer.composeapp.generated.resources.settings_mode_window

@Composable
fun FullScreenSwitch(
    fullScreenModeEnabled: Boolean,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration: PlatformConfiguration by rememberInstance()
    var canDrawOverlay by remember { mutableStateOf(canDrawOverlay(configuration)) }

    LifecycleResumeEffect(true) {
        canDrawOverlay = canDrawOverlay(configuration)
        onPauseOrDispose {}
    }

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
                enabled = canDrawOverlay,
                onFullScreenClick = { onEvent(SettingsEvent.FullScreenModeChanged(fullScreen = true)) },
                onWindowClick = { onEvent(SettingsEvent.FullScreenModeChanged(fullScreen = false)) },
            )
        },
        modifier = modifier.alpha(if (canDrawOverlay) 1f else 0.3f)
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

expect fun canDrawOverlay(configuration: PlatformConfiguration): Boolean
