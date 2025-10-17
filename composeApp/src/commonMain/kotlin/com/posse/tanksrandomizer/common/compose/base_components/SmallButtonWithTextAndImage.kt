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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize

@Composable
fun SmallButtonWithTextAndImage(
    text: String,
    painter: Painter,
    textFirst: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .height(LocalElementSize.current)
            .clickable(onClick = onClick)
            .clip(ButtonsShapeSmall)
            .border(
                width = BorderWidth,
                color = MaterialTheme.colorScheme.onSurface,
                shape = ButtonsShapeSmall
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        if (textFirst) {
            ButtonText(
                text = text,
            )
            Spacer(Modifier.width(6.dp))
        }

        Image(
            painter = painter,
            contentDescription = text,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxHeight()
        )

        if (!textFirst) {
            Spacer(Modifier.width(6.dp))
            ButtonText(
                text = text,
            )
        }
    }
}

@Composable
private fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text.uppercase(),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodySmall,
        fontSize = 10.sp,
        fontWeight = FontWeight.Black,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}
