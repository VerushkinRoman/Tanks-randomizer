package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ScreenLockRotation
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.rounded.StayCurrentLandscape
import androidx.compose.material.icons.rounded.StayCurrentPortrait
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerSwitch
import com.posse.tanksrandomizer.common.compose.utils.RotateDirection
import com.posse.tanksrandomizer.common.presentation.model.Rotation
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.auto_rotate
import tanks_randomizer.composeapp.generated.resources.lock_rotation
import tanks_randomizer.composeapp.generated.resources.rotate_landscape
import tanks_randomizer.composeapp.generated.resources.rotate_portrait

@Composable
fun RotationBlock(
    rotation: Rotation,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    RotateDevice(rotation)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            imageVector = Icons.Default.ScreenRotation,
            contentDescription = stringResource(Res.string.auto_rotate),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
        )

        Spacer(modifier = Modifier.width(12.dp))

        RandomizerSwitch(
            checked = !rotation.autoRotateEnabled,
            onClick = { onEvent(SettingsEvent.RotateSwitchChecked) },
        )

        Spacer(modifier = Modifier.width(12.dp))

        LockRotationButtons(
            autoRotationEnabled = rotation.autoRotateEnabled,
            rotateDirection = rotation.rotateDirection,
            onLandscapeRotateClick = { onEvent(SettingsEvent.LandscapeRotatePressed) },
            onPortraitRotateClick = { onEvent(SettingsEvent.PortraitRotatePressed) },
        )
    }
}

@Composable
fun LockRotationButtons(
    autoRotationEnabled: Boolean,
    rotateDirection: RotateDirection,
    onLandscapeRotateClick: () -> Unit,
    onPortraitRotateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotationButtonsAlpha by animateFloatAsState(
        targetValue = if (autoRotationEnabled) 0.3f else 1f
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            imageVector = Icons.Default.ScreenLockRotation,
            contentDescription = stringResource(Res.string.lock_rotation),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
        )

        Spacer(modifier = Modifier.width(6.dp))

        val landscapeBorder by animateColorAsState(
            targetValue = if (rotateDirection == RotateDirection.Landscape
                && !autoRotationEnabled
            ) MaterialTheme.colorScheme.surfaceBright else Color.Transparent
        )
        Image(
            imageVector = Icons.Rounded.StayCurrentLandscape,
            contentDescription = stringResource(Res.string.rotate_landscape),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.clickable(
                enabled = !autoRotationEnabled,
                onClick = onLandscapeRotateClick,
            )
                .border(width = 1.dp, color = landscapeBorder)
                .padding(4.dp)
                .alpha(rotationButtonsAlpha)
        )

        Spacer(modifier = Modifier.width(2.dp))

        val portraitBorder by animateColorAsState(
            targetValue = if (rotateDirection == RotateDirection.Portrait
                && !autoRotationEnabled
            ) MaterialTheme.colorScheme.surfaceBright else Color.Transparent
        )
        Image(
            imageVector = Icons.Rounded.StayCurrentPortrait,
            contentDescription = stringResource(Res.string.rotate_portrait),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.clickable(
                enabled = !autoRotationEnabled,
                onClick = onPortraitRotateClick,
            )
                .border(width = 1.dp, color = portraitBorder)
                .padding(4.dp)
                .alpha(rotationButtonsAlpha)
        )
    }
}

@Composable
expect fun RotateDevice(rotation: Rotation)