package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.common.compose.base_components.SmallButtonWithTextAndImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.clear_filter
import tanks_randomizer.composeapp.generated.resources.trash

@Composable
fun TrashButton(
    onTrashClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SmallButtonWithTextAndImage(
        text = stringResource(Res.string.clear_filter),
        painter = painterResource(Res.drawable.trash),
        onClick = onTrashClick,
        modifier = modifier,
    )
}
