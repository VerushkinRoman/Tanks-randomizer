package com.posse.tanksrandomizer.feature_webview_screen.compose.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.feature_webview_screen.compose.model.NavigationAction

@Composable
internal fun ActionButtonsRow(
    onAction: (NavigationAction) -> Unit,
    forwardEnabled: Boolean,
    backEnabled: Boolean,
    reloadEnabled: Boolean,
    modifier: Modifier,
) {
    val density = LocalDensity.current

    var maxWidth by remember { mutableStateOf(0.dp) }
    val buttonsSpace = remember { 8.dp }
    val buttonsWidth = remember(maxWidth) {
        minOf(
            maxWidth / 3 - buttonsSpace * 2,
            ButtonDefaults.MinHeight
        )
    }
    val buttonsShape = Modifier
        .size(buttonsWidth)
        .clip(CircleShape)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                maxWidth = with(density) { it.size.width.toDp() }
            }
    ) {
        IconButton(
            onClick = { onAction(NavigationAction.GoBack) },
            enabled = backEnabled,
            modifier = buttonsShape
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(Modifier.width(buttonsSpace))

        AnimatedContent(
            targetState = reloadEnabled,
        ) { enabled ->
            if (enabled) {
                IconButton(
                    onClick = { onAction(NavigationAction.Reload) },
                    modifier = buttonsShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                IconButton(
                    onClick = { onAction(NavigationAction.StopLoading) },
                    modifier = buttonsShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

        }

        Spacer(Modifier.width(buttonsSpace))

        IconButton(
            onClick = { onAction(NavigationAction.GoForward) },
            enabled = forwardEnabled,
            modifier = buttonsShape
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
