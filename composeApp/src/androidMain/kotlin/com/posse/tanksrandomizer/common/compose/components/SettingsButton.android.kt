package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeLarge
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.settings

@Composable
actual fun SettingsButton(
    settingsPressed: () -> Unit,
    modifier: Modifier
) {
    Image(
        imageVector = Icons.Rounded.Settings,
        contentDescription = stringResource(Res.string.settings),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
        modifier = modifier
            .size(ButtonDefaults.MinHeight)
            .clickable(onClick = settingsPressed)
            .border(
                width = BorderWidth,
                color = MaterialTheme.colorScheme.primary,
                shape = ButtonsShapeLarge
            )
            .clip(ButtonsShapeLarge)
            .padding(horizontal = 4.dp)
    )
}
