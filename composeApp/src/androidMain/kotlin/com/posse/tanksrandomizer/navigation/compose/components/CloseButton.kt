package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.close_app

@Composable
internal fun CloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val safeDrawing = WindowInsets.safeDrawing.asPaddingValues()

    Image(
        imageVector = Icons.Rounded.Close,
        contentDescription = stringResource(Res.string.close_app),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
        modifier = modifier
            .padding(
                top = safeDrawing.calculateTopPadding(),
                start = safeDrawing.calculateStartPadding(LocalLayoutDirection.current),
            )
            .size(ButtonDefaults.MinHeight)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(6.dp)
            .clickable(onClick = onClick)
    )
}
