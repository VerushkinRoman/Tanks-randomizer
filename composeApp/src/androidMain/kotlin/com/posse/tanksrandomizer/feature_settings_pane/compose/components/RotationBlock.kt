package com.posse.tanksrandomizer.feature_settings_pane.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ScreenRotation
import androidx.compose.material.icons.rounded.StayCurrentLandscape
import androidx.compose.material.icons.rounded.StayCurrentPortrait
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.posse.tanksrandomizer.common.compose.theme.themedSegmentedButtonColors
import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation
import com.posse.tanksrandomizer.common.compose.utils.rotateDevice
import com.posse.tanksrandomizer.feature_settings_pane.compose.components.common.CommonSettingsActionBlock
import com.posse.tanksrandomizer.feature_settings_pane.compose.components.common.CommonSettingsActionTitle
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsEvent
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.settings_screen_rotation_desc
import tanks_randomizer.composeapp.generated.resources.settings_screen_rotation_orientation_auto
import tanks_randomizer.composeapp.generated.resources.settings_screen_rotation_orientation_landscape
import tanks_randomizer.composeapp.generated.resources.settings_screen_rotation_orientation_portrait
import tanks_randomizer.composeapp.generated.resources.settings_screen_rotation_title

@Composable
fun RotationBlock(
    screenRotation: ScreenRotation,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LaunchedEffect(screenRotation) {
        context.rotateDevice()
    }

    CommonSettingsActionBlock(
        caption = { centered ->
            RotationDescription(
                centered = centered,
                screenRotation = screenRotation,
            )
        },
        action = {
            RotationControlButtons(
                screenRotation = screenRotation,
                onAutoRotateClick = { onEvent(SettingsEvent.RotationChanged(ScreenRotation.Auto)) },
                onPortraitRotateClick = { onEvent(SettingsEvent.RotationChanged(ScreenRotation.Portrait)) },
                onLandscapeRotateClick = { onEvent(SettingsEvent.RotationChanged(ScreenRotation.Landscape)) },
            )
        },
        modifier = modifier
    )
}

@Composable
private fun RotationDescription(
    centered: Boolean,
    screenRotation: ScreenRotation,
    modifier: Modifier = Modifier,
) {
    val descriptionText = stringResource(Res.string.settings_screen_rotation_desc) +
            " " +
            stringResource(
                when (screenRotation) {
                    ScreenRotation.Portrait -> Res.string.settings_screen_rotation_orientation_portrait
                    ScreenRotation.Landscape -> Res.string.settings_screen_rotation_orientation_landscape
                    ScreenRotation.Auto -> Res.string.settings_screen_rotation_orientation_auto
                }
            )

    CommonSettingsActionTitle(
        centered = centered,
        title = stringResource(Res.string.settings_screen_rotation_title),
        subtitle = descriptionText,
        modifier = modifier
    )
}

@Composable
private fun RotationControlButtons(
    screenRotation: ScreenRotation,
    onAutoRotateClick: () -> Unit,
    onLandscapeRotateClick: () -> Unit,
    onPortraitRotateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 0,
                count = 3,
            ),
            onClick = onAutoRotateClick,
            selected = screenRotation == ScreenRotation.Auto,
            colors = themedSegmentedButtonColors(),
            label = {
                Icon(
                    imageVector = Icons.Rounded.ScreenRotation,
                    contentDescription = stringResource(Res.string.settings_screen_rotation_orientation_auto),
                )
            },
        )

        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 3,
            ),
            onClick = onPortraitRotateClick,
            selected = screenRotation == ScreenRotation.Portrait,
            colors = themedSegmentedButtonColors(),
            label = {
                Icon(
                    imageVector = Icons.Rounded.StayCurrentPortrait,
                    contentDescription = stringResource(Res.string.settings_screen_rotation_orientation_portrait),
                )
            },
        )

        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 2,
                count = 3,
            ),
            onClick = onLandscapeRotateClick,
            selected = screenRotation == ScreenRotation.Landscape,
            colors = themedSegmentedButtonColors(),
            label = {
                Icon(
                    imageVector = Icons.Rounded.StayCurrentLandscape,
                    contentDescription = stringResource(Res.string.settings_screen_rotation_orientation_landscape),
                )
            },
        )
    }
}
