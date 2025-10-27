package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdditionalBottomComponents(
    onSettingsPressed: () -> Unit,
    additionalButton: @Composable () -> Unit,
    modifier: Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(1f))

        SettingsButton(settingsPressed = onSettingsPressed)

        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.weight(1f),
        ) {
            additionalButton()
        }
    }
}
