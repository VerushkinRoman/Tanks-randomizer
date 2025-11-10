package com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeLarge
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.lesta_logo
import tanks_randomizer.composeapp.generated.resources.login_online

@Composable
internal fun MainScreenLogInButton(
    loading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentPadding = ButtonDefaults.TextButtonWithIconContentPadding
    val imageHeight = ButtonDefaults.MinHeight -
            contentPadding.calculateTopPadding() -
            contentPadding.calculateBottomPadding()

    val buttonAlpha by animateFloatAsState(
        targetValue = if (loading) 0.3f else 1f
    )

    OutlinedButton(
        onClick = onClick,
        shape = ButtonsShapeLarge,
        enabled = !loading,
        contentPadding = contentPadding,
        border = BorderStroke(width = BorderWidth, color = MaterialTheme.colorScheme.onSurface),
        modifier = modifier.graphicsLayer { alpha = buttonAlpha }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.weight(1f)
            ) {
                LestaLogo(imageHeight)
            }

            LoginText()

            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
private fun LestaLogo(
    imageHeight: Dp,
) {
    Image(
        painter = painterResource(Res.drawable.lesta_logo),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
        modifier = Modifier.height(imageHeight),
    )
}

@Composable
private fun LoginText(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(Res.string.login_online).uppercase(),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Black,
        modifier = modifier,
    )
}
