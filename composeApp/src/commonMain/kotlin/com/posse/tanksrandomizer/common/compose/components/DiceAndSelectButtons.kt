package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BigButtonWithImage
import com.posse.tanksrandomizer.common.compose.base_components.SmallButtonWithTextAndImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.check_all
import tanks_randomizer.composeapp.generated.resources.clear_filter
import tanks_randomizer.composeapp.generated.resources.dice
import tanks_randomizer.composeapp.generated.resources.trash

@Composable
fun DiceAndSelectButtons(
    onDiceClick: () -> Unit,
    onTrashClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    allDisabled: Boolean,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier,
        content = {
            SelectAllButton(
                onSelectAllClick = onSelectAllClick,
            )

            DiceButton(
                onDiceClick = onDiceClick,
                allDisabled = allDisabled,
            )

            TrashButton(
                onTrashClick = onTrashClick,
            )
        }
    ) { measurables, constraints ->
        val spacingPx = 12.dp.roundToPx()

        val selectAllMeasurable = measurables[0]
        val diceMeasurable = measurables[1]
        val trashMeasurable = measurables[2]

        val selectAllMaxWidth = selectAllMeasurable.maxIntrinsicWidth(constraints.maxHeight)
        val trashMaxWidth = trashMeasurable.maxIntrinsicWidth(constraints.maxHeight)

        val maxWidth = maxOf(selectAllMaxWidth, trashMaxWidth)

        val fixedSelectAllPlaceable = selectAllMeasurable.measure(
            constraints.copy(
                minWidth = maxWidth,
                maxWidth = maxWidth
            )
        )

        val fixedTrashPlaceable = trashMeasurable.measure(
            constraints.copy(
                minWidth = maxWidth,
                maxWidth = maxWidth
            )
        )

        val totalSpacingWidth = spacingPx * 2
        val availableWidthForDice = constraints.maxWidth -
                fixedSelectAllPlaceable.width -
                fixedTrashPlaceable.width -
                totalSpacingWidth

        val diceWidth = maxOf(0, availableWidthForDice)

        val dicePlaceable = diceMeasurable.measure(
            constraints.copy(
                minWidth = diceWidth,
                maxWidth = diceWidth
            )
        )

        val totalHeight = maxOf(
            fixedSelectAllPlaceable.height,
            dicePlaceable.height,
            fixedTrashPlaceable.height
        )

        layout(constraints.maxWidth, totalHeight) {
            fixedSelectAllPlaceable.placeRelative(
                0,
                0
            )
            dicePlaceable.placeRelative(
                fixedSelectAllPlaceable.width + spacingPx,
                0
            )
            fixedTrashPlaceable.placeRelative(
                fixedSelectAllPlaceable.width + spacingPx + dicePlaceable.width + spacingPx,
                0
            )
        }
    }
}

@Composable
private fun DiceButton(
    onDiceClick: () -> Unit,
    allDisabled: Boolean,
    modifier: Modifier = Modifier,
) {
    BigButtonWithImage(
        painter = painterResource(Res.drawable.dice),
        contentDescription = stringResource(Res.string.dice),
        enabled = !allDisabled,
        onClick = onDiceClick,
        modifier = modifier,
    )
}

@Composable
private fun TrashButton(
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

@Composable
private fun SelectAllButton(
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
