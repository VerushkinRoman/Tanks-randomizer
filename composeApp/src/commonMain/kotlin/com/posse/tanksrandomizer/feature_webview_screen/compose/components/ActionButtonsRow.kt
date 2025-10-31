package com.posse.tanksrandomizer.feature_webview_screen.compose.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.ButtonWithImage
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeLarge
import com.posse.tanksrandomizer.feature_webview_screen.compose.model.NavigationAction
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.exit
import tanks_randomizer.composeapp.generated.resources.go_back
import tanks_randomizer.composeapp.generated.resources.go_forward
import tanks_randomizer.composeapp.generated.resources.refresh_page
import tanks_randomizer.composeapp.generated.resources.stop_loading

@Composable
internal fun ActionButtonsRow(
    additionalPadding: Boolean,
    onAction: (NavigationAction) -> Unit,
    forwardEnabled: Boolean,
    backEnabled: Boolean,
    reloadEnabled: Boolean,
    modifier: Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.then(
            if (additionalPadding) Modifier.padding(horizontal = ButtonDefaults.MinHeight + 16.dp)
            else Modifier
        )
    ) {
        GoBackButton(
            onClick = { onAction(NavigationAction.Exit) },
        )

        Spacer(Modifier.weight(1f))

        BackButton(
            enabled = backEnabled,
            onClick = { onAction(NavigationAction.GoBack) },
        )

        StopAndReloadButton(
            reloadEnabled = reloadEnabled,
            onReloadClick = { onAction(NavigationAction.Reload) },
            onStopClick = { onAction(NavigationAction.StopLoading) },
        )

        ForwardButton(
            enabled = forwardEnabled,
            onClick = { onAction(NavigationAction.GoForward) },
        )
    }
}

@Composable
private fun GoBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentPadding = ButtonDefaults.TextButtonWithIconContentPadding
    val imageHeight = ButtonDefaults.MinHeight -
            contentPadding.calculateTopPadding() -
            contentPadding.calculateBottomPadding()

    OutlinedButton(
        onClick = onClick,
        shape = ButtonsShapeLarge,
        contentPadding = contentPadding,
        border = BorderStroke(width = BorderWidth, color = MaterialTheme.colorScheme.onSurface),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = stringResource(Res.string.exit),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.height(imageHeight),
            )

            Text(
                text = stringResource(Res.string.exit).uppercase(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Black,
            )
        }
    }
}

@Composable
private fun BackButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonWithImage(
        painter = rememberVectorPainter(Icons.AutoMirrored.Rounded.ArrowBack),
        contentDescription = stringResource(Res.string.go_back),
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun ForwardButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ButtonWithImage(
        painter = rememberVectorPainter(Icons.AutoMirrored.Rounded.ArrowForward),
        contentDescription = stringResource(Res.string.go_forward),
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
    )
}

@Composable
private fun StopAndReloadButton(
    reloadEnabled: Boolean,
    onReloadClick: () -> Unit,
    onStopClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = reloadEnabled,
        modifier = modifier,
    ) { enabled ->
        if (enabled) {
            ButtonWithImage(
                painter = rememberVectorPainter(Icons.Rounded.Refresh),
                contentDescription = stringResource(Res.string.refresh_page),
                onClick = onReloadClick,
            )
        } else {
            ButtonWithImage(
                painter = rememberVectorPainter(Icons.Rounded.Close),
                contentDescription = stringResource(Res.string.stop_loading),
                onClick = onStopClick,
            )
        }
    }
}
