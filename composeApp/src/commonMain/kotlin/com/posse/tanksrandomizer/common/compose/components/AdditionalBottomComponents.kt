package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            SettingsButton(settingsPressed = onSettingsPressed)

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.weight(1f)
            ) {
                additionalButton()
            }
        }
    }
}
