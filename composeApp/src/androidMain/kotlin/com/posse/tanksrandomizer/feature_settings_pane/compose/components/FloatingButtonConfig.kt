package com.posse.tanksrandomizer.feature_settings_pane.compose.components

import androidx.annotation.IntRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.FullscreenExit
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.domain.models.ButtonSize
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsState
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.less_opacity
import tanks_randomizer.composeapp.generated.resources.less_size
import tanks_randomizer.composeapp.generated.resources.more_opacity
import tanks_randomizer.composeapp.generated.resources.more_size
import kotlin.math.roundToInt

@Composable
fun FloatingButtonConfig(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        OpacityBlock(
            opacity = state.buttonOpacity,
            onOpacityChange = { onEvent(SettingsEvent.SetButtonOpacity(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        SizeBlock(
            size = state.buttonSize,
            onSizeChange = { onEvent(SettingsEvent.SetButtonSize(it)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun OpacityBlock(
    opacity: Float,
    onOpacityChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    CommonBlock(
        value = opacity,
        onValueChane = onOpacityChange,
        valueRange = 0.1f..1f,
        steps = 7,
        startIcon = Icons.Rounded.VisibilityOff,
        endIcon = Icons.Rounded.Visibility,
        startContentDescription = stringResource(Res.string.less_opacity),
        endContentDescription = stringResource(Res.string.more_opacity),
        modifier = modifier
    )
}

@Composable
private fun SizeBlock(
    size: ButtonSize,
    onSizeChange: (ButtonSize) -> Unit,
    modifier: Modifier = Modifier
) {
    CommonBlock(
        value = size.ordinal.toFloat(),
        onValueChane = { value ->
            ButtonSize.entries
                .find { it.ordinal == value.roundToInt() }
                ?.let { onSizeChange(it) }
        },
        valueRange = 0f..(ButtonSize.entries.size - 1).toFloat(),
        steps = (ButtonSize.entries.size - 2).coerceAtLeast(0),
        startIcon = Icons.Rounded.FullscreenExit,
        endIcon = Icons.Rounded.Fullscreen,
        startContentDescription = stringResource(Res.string.less_size),
        endContentDescription = stringResource(Res.string.more_size),
        modifier = modifier
    )
}

@Composable
private fun CommonBlock(
    value: Float,
    onValueChane: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    @IntRange(from = 0) steps: Int,
    startIcon: ImageVector,
    endIcon: ImageVector,
    startContentDescription: String?,
    endContentDescription: String?,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            imageVector = startIcon,
            contentDescription = startContentDescription,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Slider(
            value = value,
            onValueChange = onValueChane,
            valueRange = valueRange,
            steps = steps,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.onSurface,
                activeTrackColor = MaterialTheme.colorScheme.onSurface,
                activeTickColor = MaterialTheme.colorScheme.onSurface,
                inactiveTrackColor = MaterialTheme.colorScheme.outline,
                inactiveTickColor = MaterialTheme.colorScheme.onSurface,
            ),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            imageVector = endIcon,
            contentDescription = endContentDescription,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
        )
    }
}