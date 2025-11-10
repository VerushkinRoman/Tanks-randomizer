package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.common.CommonSettingsActionBlock
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.common.CommonSettingsActionTitle
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.settings_overlay_button
import tanks_randomizer.composeapp.generated.resources.settings_overlay_desc
import tanks_randomizer.composeapp.generated.resources.settings_overlay_title

@Composable
fun OverlayPermissionControl(
    openOverlaySettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CommonSettingsActionBlock(
        caption = { centered ->
            CommonSettingsActionTitle(
                centered = centered,
                title = stringResource(Res.string.settings_overlay_title),
                subtitle = stringResource(Res.string.settings_overlay_desc),
            )
        },
        action = {
            OutlinedIconButton(
                onClick = openOverlaySettings,
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                border = BorderStroke(
                    width = BorderWidth,
                    color = MaterialTheme.colorScheme.onBackground,
                ),
            ) {
                Icon(
                    painter = rememberVectorPainter(Icons.Rounded.Tune),
                    contentDescription = stringResource(Res.string.settings_overlay_button),
                )
            }
        },
        modifier = modifier
    )
}
