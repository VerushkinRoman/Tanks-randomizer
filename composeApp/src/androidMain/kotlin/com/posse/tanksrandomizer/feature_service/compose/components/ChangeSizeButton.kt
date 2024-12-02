package com.posse.tanksrandomizer.feature_service.compose.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloseFullscreen
import androidx.compose.material.icons.rounded.OpenInFull
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
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
    AppTheme {
        AnimatedContent(
            targetState = windowInFullScreen,
            modifier = modifier
                .size(ButtonDefaults.MinHeight)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(6.dp)
        ) { fullScreen ->
            Image(
                imageVector = if (fullScreen) Icons.Rounded.CloseFullscreen else Icons.Rounded.OpenInFull,
                contentDescription = stringResource(if (fullScreen) Res.string.collapse_app else Res.string.expand_app),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            )
        }
    }
}