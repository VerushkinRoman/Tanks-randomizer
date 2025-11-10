package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

@OptIn(markerClass = [ExperimentalMaterial3Api::class])
@Composable
actual fun PlatformTrack(
    sliderState: SliderState,
    thumbTrackGapSize: Dp,
    drawStopIndicator: (DrawScope.(Offset) -> Unit)?,
    trackCornerSize: Dp,
    modifier: Modifier
) = SliderDefaults.Track(
    sliderState = sliderState,
    thumbTrackGapSize = thumbTrackGapSize,
    drawStopIndicator = drawStopIndicator,
    trackInsideCornerSize = trackCornerSize,
    modifier = modifier
)
