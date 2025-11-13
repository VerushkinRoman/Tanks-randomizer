package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.material.icons.rounded.FullscreenExit
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.posse.tanksrandomizer.common.compose.utils.getScreenSize
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsState
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.settings_opacity_title
import tanks_randomizer.composeapp.generated.resources.settings_size_title
import kotlin.math.roundToInt

@Composable
fun FloatingButtonConfig(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    columnSpacer: Dp,
    modifier: Modifier = Modifier
) {
    var minWidth by remember { mutableIntStateOf(0) }

    Column(
        verticalArrangement = Arrangement.spacedBy(columnSpacer),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        OpacityBlock(
            opacity = state.buttonOpacity,
            onOpacityChange = { onEvent(SettingsEvent.SetButtonOpacity(it)) },
            onWidthChange = {
                minWidth = if (minWidth == 0) it
                else minOf(minWidth, it)
            },
            maxWidth = minWidth,
            modifier = Modifier.fillMaxWidth()
        )

        SizeBlock(
            size = state.buttonSize,
            onSizeChange = { onEvent(SettingsEvent.SetButtonSize(it)) },
            onWidthChange = {
                minWidth = if (minWidth == 0) it
                else minOf(minWidth, it)
            },
            maxWidth = minWidth,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun OpacityBlock(
    opacity: Float,
    onOpacityChange: (Float) -> Unit,
    onWidthChange: (Int) -> Unit,
    maxWidth: Int,
    modifier: Modifier = Modifier,
) {
    CommonBlock(
        value = opacity,
        onValueChange = onOpacityChange,
        valueRange = 0.1f..1f,
        startIcon = rememberVectorPainter(Icons.Rounded.VisibilityOff),
        endIcon = rememberVectorPainter(Icons.Rounded.Visibility),
        titleText = stringResource(Res.string.settings_opacity_title),
        onWidthChange = onWidthChange,
        maxWidth = maxWidth,
        modifier = modifier,
    )
}

@Composable
private fun SizeBlock(
    size: Float,
    onSizeChange: (Float) -> Unit,
    onWidthChange: (Int) -> Unit,
    maxWidth: Int,
    modifier: Modifier = Modifier,
) {
    CommonBlock(
        value = size,
        onValueChange = onSizeChange,
        valueRange = 0.5f..1f,
        startIcon = rememberVectorPainter(Icons.Rounded.FullscreenExit),
        endIcon = rememberVectorPainter(Icons.Rounded.Fullscreen),
        titleText = stringResource(Res.string.settings_size_title),
        onWidthChange = onWidthChange,
        maxWidth = maxWidth,
        modifier = modifier,
    )
}

@Composable
private fun CommonBlock(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    startIcon: VectorPainter,
    endIcon: VectorPainter,
    titleText: String,
    onWidthChange: (Int) -> Unit,
    maxWidth: Int,
    modifier: Modifier = Modifier,
) {
    val title: @Composable () -> Unit = {
        Text(
            text = titleText,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }

    val slider: @Composable () -> Unit = {
        SettingsSlider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            startIcon = startIcon,
            endIcon = endIcon,
            modifier = Modifier.fillMaxWidth()
        )
    }

    when (getScreenSize()) {
        ScreenSize.Small, ScreenSize.Large -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
            ) {
                title()
                slider()
            }
        }

        ScreenSize.Medium -> {
            val density = LocalDensity.current

            BoxWithConstraints(modifier = modifier) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {

                    val maxWidthDp = this@BoxWithConstraints.maxWidth * 0.5f

                    Box(
                        modifier = Modifier
                            .widthIn(max = maxWidthDp)
                            .wrapContentWidth()
                    ) {
                        title()
                    }

                    Spacer(Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .onSizeChanged { size ->
                                onWidthChange(size.width)
                            }
                            .width(if (maxWidth == 0) Dp.Unspecified else with(density) { maxWidth.toDp() })
                    ) {
                        slider()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    startIcon: VectorPainter,
    endIcon: VectorPainter,
    modifier: Modifier = Modifier,
) {
    val sliderState = rememberSliderState(
        value = value,
        valueRange = valueRange,
    )

    LaunchedEffect(sliderState) {
        snapshotFlow { sliderState.value }
            .collect { value ->
                val roundedValue = (value * 100).roundToInt() / 100.0f
                onValueChange(roundedValue)
            }
    }

    val sliderColors = SliderDefaults.colors(
        thumbColor = MaterialTheme.colorScheme.primary,
        activeTrackColor = MaterialTheme.colorScheme.onPrimaryContainer,
        inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer,
    )

    Slider(
        state = sliderState,
        colors = sliderColors,
        track = {
            val iconSize = DpSize(20.dp, 20.dp)
            val iconPadding = 10.dp
            val thumbTrackGapSize = 6.dp
            val trackCornerSize = 12.dp
            val activeIconColor = MaterialTheme.colorScheme.primary
            val inactiveIconColor = MaterialTheme.colorScheme.primary
            val trackIconStart: DrawScope.(Offset, Color) -> Unit = { offset, color ->
                translate(offset.x + iconPadding.toPx(), offset.y) {
                    with(startIcon) {
                        draw(
                            iconSize.toSize(),
                            colorFilter = ColorFilter.tint(color)
                        )
                    }
                }
            }
            val trackIconEnd: DrawScope.(Offset, Color) -> Unit = { offset, color ->
                translate(offset.x - iconPadding.toPx() - iconSize.toSize().width, offset.y) {
                    with(endIcon) {
                        draw(iconSize.toSize(), colorFilter = ColorFilter.tint(color))
                    }
                }
            }
            val trackModifier = Modifier
                .height(36.dp)
                .drawWithContent {
                    drawContent()

                    val yOffset = size.height / 2 - iconSize.toSize().height / 2

                    val activeTrackStart = 0f
                    val activeTrackEnd =
                        size.width * sliderState.coercedValueAsFraction - thumbTrackGapSize.toPx()

                    val inactiveTrackStart = activeTrackEnd + thumbTrackGapSize.toPx() * 2
                    val inactiveTrackEnd = size.width

                    val activeTrackWidth = activeTrackEnd - activeTrackStart
                    val inactiveTrackWidth = inactiveTrackEnd - inactiveTrackStart

                    if (iconSize.toSize().width < activeTrackWidth - iconPadding.toPx() * 2) {
                        trackIconStart(
                            Offset(activeTrackStart, yOffset),
                            activeIconColor,
                        )
                    } else {
                        trackIconStart(
                            Offset(inactiveTrackStart, yOffset),
                            inactiveIconColor,
                        )
                    }

                    if (iconSize.toSize().width < inactiveTrackWidth - iconPadding.toPx() * 2) {
                        trackIconEnd(
                            Offset(inactiveTrackEnd, yOffset),
                            inactiveIconColor,
                        )
                    } else {
                        trackIconEnd(
                            Offset(activeTrackEnd, yOffset),
                            activeIconColor,
                        )
                    }
                }
                .border(
                    width = BorderWidth,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(trackCornerSize),
                )

            PlatformTrack(
                sliderState = sliderState,
                thumbTrackGapSize = thumbTrackGapSize,
                colors = sliderColors,
                trackCornerSize = trackCornerSize,
                modifier = trackModifier,
            )
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
expect fun PlatformTrack(
    sliderState: SliderState,
    thumbTrackGapSize: Dp,
    drawStopIndicator: (DrawScope.(Offset) -> Unit)? = null,
    colors: SliderColors,
    trackCornerSize: Dp,
    modifier: Modifier,
)
