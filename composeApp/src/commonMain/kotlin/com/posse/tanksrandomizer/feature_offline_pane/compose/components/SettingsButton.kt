package com.posse.tanksrandomizer.feature_offline_pane.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneEvent
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.settings

@Composable
internal fun SettingsButton(
    onEvent: (OfflinePaneEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        imageVector = Icons.Rounded.Settings,
        contentDescription = stringResource(Res.string.settings),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
        modifier = modifier.clickable { onEvent(OfflinePaneEvent.SettingsPressed) }
    )
}