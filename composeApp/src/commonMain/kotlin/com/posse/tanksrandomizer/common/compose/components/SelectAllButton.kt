package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.posse.tanksrandomizer.common.compose.base_components.SmallButtonWithTextAndImage
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.check_all

@Composable
fun SelectAllButton(
    onSelectAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SmallButtonWithTextAndImage(
        text = stringResource(Res.string.check_all),
        painter = rememberVectorPainter(Icons.Rounded.DoneAll),
        textFirst = false,
        onClick = onSelectAllClick,
        modifier = modifier,
    )
}
