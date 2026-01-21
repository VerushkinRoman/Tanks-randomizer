package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.sp
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.compose.theme.themedSegmentedButtonColors
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.common.CommonSettingsActionBlock
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.common.CommonSettingsActionTitle
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.settings_autohide_title
import tanks_randomizer.composeapp.generated.resources.settings_multiaccount_btn_off
import tanks_randomizer.composeapp.generated.resources.settings_multiaccount_btn_on

@Composable
fun AutoHideSwitch(
    autoHideEnabled: Boolean,
    enabled: Boolean,
    onAutoHideChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    CommonSettingsActionBlock(
        caption = { centered ->
            CommonSettingsActionTitle(
                centered = centered,
                title = stringResource(Res.string.settings_autohide_title),
            )
        },
        action = {
            AutoHideSwitchControlButtons(
                autoHideEnabled = autoHideEnabled,
                enabled = enabled,
                onAutoHideChanged = onAutoHideChanged,
            )
        },
        modifier = modifier.alpha(if (enabled) 1f else 0.3f)
    )
}

@Composable
private fun AutoHideSwitchControlButtons(
    autoHideEnabled: Boolean,
    enabled: Boolean,
    onAutoHideChanged: (Boolean) -> Unit,
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
            onClick = { onAutoHideChanged(true) },
            selected = autoHideEnabled,
            enabled = enabled,
            colors = themedSegmentedButtonColors(),
            label = {
                RandomizerText(
                    text = stringResource(Res.string.settings_multiaccount_btn_on),
                    fontSize = 14.sp,
                    capitalize = false
                )
            },
        )

        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 2,
            ),
            onClick = { onAutoHideChanged(false) },
            selected = !autoHideEnabled,
            enabled = enabled,
            colors = themedSegmentedButtonColors(),
            label = {
                RandomizerText(
                    text = stringResource(Res.string.settings_multiaccount_btn_off),
                    fontSize = 14.sp,
                    capitalize = false
                )
            },
        )
    }
}
