package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize

@Composable
fun SmallButtonWithTextAndImage(
    text: String,
    painter: Painter? = null,
    textFirst: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val verticalPadding = 4.dp

    var textHeight by remember { mutableStateOf(16.dp) }
    val onTextLayout: ((TextLayoutResult) -> Unit) = { layoutResults ->
        textHeight = with(density) { layoutResults.size.height.toDp() }
    }
    val buttonHeight = maxOf(
        LocalElementSize.current,
        textHeight + verticalPadding * 2
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .height(buttonHeight)
            .then(
                if (enabled) Modifier.clickable(onClick = onClick)
                else Modifier
            )
            .clip(ButtonsShapeLarge)
            .border(
                width = BorderWidth,
                color = MaterialTheme.colorScheme.primary,
                shape = ButtonsShapeLarge
            )
            .padding(horizontal = 12.dp, vertical = verticalPadding)
    ) {
        if (textFirst) {
            RandomizerText(
                text = text,
                onTextLayout = onTextLayout,
            )
            if (painter != null) Spacer(Modifier.width(6.dp))
        }

        painter?.let {
            Image(
                painter = painter,
                contentDescription = text,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxHeight()
            )
        }

        if (!textFirst) {
            if (painter != null) Spacer(Modifier.width(6.dp))
            RandomizerText(
                text = text,
                onTextLayout = onTextLayout,
            )
        }
    }
}
