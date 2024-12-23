package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloseFullscreen
import androidx.compose.material.icons.rounded.OpenInFull
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerSwitch
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.fullscreen_mode
import tanks_randomizer.composeapp.generated.resources.overlay_mode
import tanks_randomizer.composeapp.generated.resources.overlay_settings

@Composable
fun FullScreenSwitch(
    autoRotation: Boolean,
    onClick: () -> Unit,
    onAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var canDrawOverlay by remember { mutableStateOf(Settings.canDrawOverlays(context)) }

    LifecycleResumeEffect(true) {
        canDrawOverlay = Settings.canDrawOverlays(context)
        onPauseOrDispose {}
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .alpha(if (canDrawOverlay) 1f else 0.3f)
        ) {
            Image(
                imageVector = Icons.Rounded.CloseFullscreen,
                contentDescription = stringResource(Res.string.overlay_mode),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            )

            Spacer(modifier = Modifier.width(12.dp))

            RandomizerSwitch(
                checked = autoRotation,
                onClick = { onClick() },
                enabled = canDrawOverlay,
            )

            Spacer(modifier = Modifier.width(6.dp))

            Image(
                imageVector = Icons.Rounded.OpenInFull,
                contentDescription = stringResource(Res.string.fullscreen_mode),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        if (!canDrawOverlay) {
            IconButton(
                onClick = onAppSettingsClick
            ) {
                Image(
                    imageVector = Icons.Rounded.Tune,
                    contentDescription = stringResource(Res.string.overlay_settings),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                    contentScale = ContentScale.FillHeight,
                )
            }
        }
    }
}
