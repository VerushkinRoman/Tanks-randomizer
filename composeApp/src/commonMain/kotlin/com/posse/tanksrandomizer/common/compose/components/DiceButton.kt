package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.common.compose.base_components.BigButtonWithImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.dice

@Composable
fun DiceButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    BigButtonWithImage(
        painter = painterResource(Res.drawable.dice),
        contentDescription = stringResource(Res.string.dice),
        enabled = enabled,
        onClick = onClick,
        modifier = modifier,
    )
}
