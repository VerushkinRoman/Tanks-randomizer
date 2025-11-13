package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize

@Composable
fun ButtonWithImage(
    painter: Painter,
    contentDescription: String,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = Color.Transparent,
    isBig: Boolean = true,
    modifier: Modifier = Modifier
) {
    val buttonAlpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.3f
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(if (isBig) ButtonDefaults.MinHeight else LocalElementSize.current)
            .widthIn(min = if (isBig) ButtonDefaults.MinHeight else LocalElementSize.current)
            .graphicsLayer { alpha = buttonAlpha }
            .then(
                if (onClick != null) Modifier.clickable(
                    enabled = enabled,
                    onClick = onClick
                ) else Modifier
            )
            .clip(if (isBig) ButtonsShapeLarge else ButtonsShapeSmall)
            .background(backgroundColor)
            .border(
                width = BorderWidth,
                color = MaterialTheme.colorScheme.primary,
                shape = if (isBig) ButtonsShapeLarge else ButtonsShapeSmall
            )
            .padding(if (isBig) 6.dp else 2.dp),
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxHeight()
        )
    }
}
