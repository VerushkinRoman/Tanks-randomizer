package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun RandomizerText(
    text: String,
    fontSize: TextUnit = 10.sp,
    capitalize: Boolean = true,
    modifier: Modifier = Modifier
) {
    Text(
        text = if (capitalize) text.uppercase() else text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodySmall,
        fontSize = fontSize,
        fontWeight = FontWeight.Black,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}
