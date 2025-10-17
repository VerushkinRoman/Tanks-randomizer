package com.posse.tanksrandomizer.feature_settings_pane.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BigButtonWithImage
import com.posse.tanksrandomizer.feature_settings_pane.compose.components.common.CommonSettingsActionTitle
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
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        CommonSettingsActionTitle(
            title = stringResource(Res.string.settings_overlay_title),
            subtitle = stringResource(Res.string.settings_overlay_desc),
            modifier = Modifier.weight(1f),
        )

        BigButtonWithImage(
            painter = rememberVectorPainter(Icons.Rounded.Tune),
            contentDescription = stringResource(Res.string.settings_overlay_button),
            onClick = openOverlaySettings,
            backgroundColor = MaterialTheme.colorScheme.primary,
        )
    }
}
