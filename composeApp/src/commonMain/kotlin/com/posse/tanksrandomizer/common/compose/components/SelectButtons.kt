package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp

@Composable
fun SelectButtons(
    onTrashClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier,
        content = {
            SelectAllButton(
                onSelectAllClick = onSelectAllClick,
            )

            TrashButton(
                onTrashClick = onTrashClick,
            )
        }
    ) { measurables, constraints ->
        val selectAllMeasurable = measurables[0]
        val trashMeasurable = measurables[1]

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

        val totalHeight = maxOf(
            fixedSelectAllPlaceable.height,
            fixedTrashPlaceable.height
        )

        val spaceWithoutButtons = constraints.maxWidth - maxWidth * 2
        val freeSpace = spaceWithoutButtons / 3
        val minSpacingPx = 16.dp.roundToPx()
        val centerSpace = maxOf(freeSpace, minSpacingPx)
        val startSpace = (spaceWithoutButtons - centerSpace) / 2

        layout(constraints.maxWidth, totalHeight) {
            fixedSelectAllPlaceable.placeRelative(
                startSpace,
                0
            )
            fixedTrashPlaceable.placeRelative(
                fixedSelectAllPlaceable.width + startSpace + centerSpace,
                0
            )
        }
    }
}
