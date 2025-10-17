package com.posse.tanksrandomizer.feature_settings_pane.compose.components.common

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun CommonSettingsActionBlock(
    caption: @Composable (centered: Boolean) -> Unit,
    action: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    caption(false)
                }
                action()
            }
        }

        else -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                caption(true)
                action()
            }
        }
    }
}
