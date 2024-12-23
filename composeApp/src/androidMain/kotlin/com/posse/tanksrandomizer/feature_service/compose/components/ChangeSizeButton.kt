package com.posse.tanksrandomizer.feature_service.compose.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloseFullscreen
import androidx.compose.material.icons.rounded.OpenInFull
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.theme.AppTheme
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.collapse_app
import tanks_randomizer.composeapp.generated.resources.expand_app

@Composable
internal fun ChangeSizeButton(
    windowInFullScreen: Boolean,
    modifier: Modifier = Modifier
) {
    var alpha by remember { mutableFloatStateOf(0f) }
    val opacity by animateFloatAsState(
        targetValue = alpha
    )

    AppTheme {
        AnimatedContent(
            targetState = windowInFullScreen,
            modifier = modifier
                .alpha(opacity)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(6.dp)
                .onSizeChanged {
                    if (alpha != 1f && it.height != 0 && it.width != 0) {
                        alpha = 1f
                    }
                }
        ) { fullScreen ->
            Image(
                imageVector = if (fullScreen) Icons.Rounded.CloseFullscreen else Icons.Rounded.OpenInFull,
                contentDescription = stringResource(if (fullScreen) Res.string.collapse_app else Res.string.expand_app),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            )
        }
    }
}