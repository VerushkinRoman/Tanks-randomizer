package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults.Track
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
actual fun PlatformTrack(
    sliderState: SliderState,
    thumbTrackGapSize: Dp,
    drawStopIndicator: (DrawScope.(Offset) -> Unit)?,
    colors: SliderColors,
    trackCornerSize: Dp,
    modifier: Modifier,
) = Track(
    sliderState = sliderState,
    colors = colors,
    trackCornerSize = trackCornerSize,
    drawStopIndicator = drawStopIndicator,
    thumbTrackGapSize = thumbTrackGapSize,
    modifier = modifier,
)
