package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RandomizerSwitch(
    checked: Boolean,
    onClick: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Switch(
        checked = checked,
        onCheckedChange = onClick,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.onSurface,
            checkedTrackColor = MaterialTheme.colorScheme.surfaceContainerLow,
            checkedBorderColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier
    )
}