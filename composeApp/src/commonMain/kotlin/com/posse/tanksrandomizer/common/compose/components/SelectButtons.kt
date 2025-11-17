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

        val buttonWidth = maxOf(selectAllMaxWidth, trashMaxWidth)

        val minSpacing = 16.dp.roundToPx()

        val requiredHorizontalWidth = (buttonWidth * 2) + minSpacing

        if (requiredHorizontalWidth > constraints.maxWidth) {
            val verticalConstraints =
                constraints.copy(minWidth = buttonWidth, maxWidth = constraints.maxWidth)
            val selectAllPlaceable = selectAllMeasurable.measure(verticalConstraints)
            val trashPlaceable = trashMeasurable.measure(verticalConstraints)

            val verticalSpacing = 12.dp.roundToPx()

            val totalHeight = selectAllPlaceable.height + verticalSpacing + trashPlaceable.height
            val startSpace = (constraints.maxWidth - buttonWidth) / 2

            layout(constraints.maxWidth, totalHeight) {
                selectAllPlaceable.placeRelative(x = startSpace, y = 0)
                trashPlaceable.placeRelative(
                    x = startSpace,
                    y = selectAllPlaceable.height + verticalSpacing
                )
            }

        } else {
            val horizontalConstraints = constraints.copy(
                minWidth = buttonWidth,
                maxWidth = buttonWidth
            )
            val selectAllPlaceable = selectAllMeasurable.measure(horizontalConstraints)
            val trashPlaceable = trashMeasurable.measure(horizontalConstraints)

            val totalHeight = maxOf(selectAllPlaceable.height, trashPlaceable.height)

            val spaceWithoutButtons = constraints.maxWidth - buttonWidth * 2
            val freeSpace = spaceWithoutButtons / 3
            val centerSpace = maxOf(freeSpace, minSpacing)
            val startSpace = (spaceWithoutButtons - centerSpace) / 2

            layout(constraints.maxWidth, totalHeight) {
                selectAllPlaceable.placeRelative(
                    x = startSpace,
                    y = 0
                )
                trashPlaceable.placeRelative(
                    x = startSpace + selectAllPlaceable.width + centerSpace,
                    y = 0
                )
            }
        }
    }
}
