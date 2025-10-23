package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun BigButtonWithImage(
    painter: Painter,
    contentDescription: String,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = Color.Transparent,
    modifier: Modifier = Modifier
) {
    val buttonAlpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.3f
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .height(ButtonDefaults.MinHeight)
            .widthIn(min = ButtonDefaults.MinHeight)
            .graphicsLayer { alpha = buttonAlpha }
            .then(
                if (onClick != null) Modifier.clickable(
                    enabled = enabled,
                    onClick = onClick
                ) else Modifier
            )
            .background(backgroundColor)
            .clip(ButtonsShapeLarge)
            .border(
                width = BorderWidth,
                color = MaterialTheme.colorScheme.onSurface,
                shape = ButtonsShapeLarge
            )
            .padding(6.dp),
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxHeight()
        )
    }
}
