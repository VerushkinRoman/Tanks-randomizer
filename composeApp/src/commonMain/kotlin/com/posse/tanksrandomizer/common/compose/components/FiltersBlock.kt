package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeSmall
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize
import com.posse.tanksrandomizer.common.compose.utils.additionalPadding
import com.posse.tanksrandomizer.common.compose.utils.colorFilter
import com.posse.tanksrandomizer.common.compose.utils.getFilterImage
import com.posse.tanksrandomizer.common.compose.utils.getFilterName
import com.posse.tanksrandomizer.common.compose.utils.useFullSize
import com.posse.tanksrandomizer.common.compose.utils.useSquare
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus

@Composable
fun FiltersBlock(
    components: List<List<ItemStatus<*>>>,
    onFilterItemClick: (ItemStatus<*>) -> Unit,
    onDiceClick: () -> Unit,
    onTrashClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val diceClicked = remember { mutableStateOf(false) }

    val allDisabled by remember(components) {
        derivedStateOf { components.flatten().all { !it.selected } }
    }

    val allCleared by remember(components) {
        derivedStateOf { components.flatten().all { !it.random } }
    }

    var prevComponentsAllCleared by remember { mutableStateOf(true) }
    LaunchedEffect(components) {
        if (prevComponentsAllCleared) diceClicked.value = false
        prevComponentsAllCleared = allCleared
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Filters(
            components = components,
            onFilterItemClick = onFilterItemClick,
            diceClicked = diceClicked,
            modifier = Modifier.fillMaxWidth()
        )

        ButtonsByWidth(
            onDiceClick = onDiceClick,
            onTrashClick = onTrashClick,
            onSelectAllClick = onSelectAllClick,
            diceEnabled = !allDisabled,
            diceClicked = diceClicked,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun ButtonsByWidth(
    onDiceClick: () -> Unit,
    onTrashClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    diceEnabled: Boolean,
    diceClicked: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val sizeClass = WindowSizeClass.calculateFromSize(
            DpSize(width = maxWidth, height = maxHeight)
        )

        when (sizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                ) {
                    SelectButtons(
                        onTrashClick = onTrashClick,
                        onSelectAllClick = onSelectAllClick,
                        modifier = Modifier.fillMaxWidth()
                    )

                    DiceButton(
                        onClick = {
                            diceClicked.value = true
                            onDiceClick()
                        },
                        enabled = diceEnabled
                    )
                }
            }

            else -> {
                DiceAndSelectButtons(
                    onDiceClick = {
                        diceClicked.value = true
                        onDiceClick()
                    },
                    onTrashClick = onTrashClick,
                    onSelectAllClick = onSelectAllClick,
                    diceEnabled = diceEnabled,
                    Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Filters(
    components: List<List<ItemStatus<*>>>,
    onFilterItemClick: (ItemStatus<*>) -> Unit,
    diceClicked: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        itemVerticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        components.forEach { component ->
            FilterItemsRow(
                items = component,
                onItemClick = { onFilterItemClick(it) },
                diceClicked = diceClicked
            )
        }
    }
}

@Composable
private fun FilterItemsRow(
    items: List<ItemStatus<*>>,
    onItemClick: (ItemStatus<*>) -> Unit,
    diceClicked: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            items.forEach { item ->
                FilterButton(
                    item = item,
                    onItemClick = { onItemClick(item) },
                    diceClicked = diceClicked,
                )
            }
        }
    }
}

@Composable
private fun FilterButton(
    item: ItemStatus<*>,
    onItemClick: () -> Unit,
    diceClicked: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val alpha by animateFloatAsState(
        targetValue = if (item.selected) 1f else 0.1f,
    )

    val defaultBorderColor =
        if (item is CommonFilterObjects.Nation) Color.Transparent
        else MaterialTheme.colorScheme.onPrimary

    val border by animateColorAsState(
        targetValue = when {
            item.random -> {
                if (!diceClicked.value) {
                    Color.Green
                } else {
                    defaultBorderColor
                }
            }

            else -> {
                defaultBorderColor
            }
        },
        finishedListener = { diceClicked.value = false },
    )

    Image(
        painter = getFilterImage(item),
        contentDescription = getFilterName(item),
        colorFilter = item.colorFilter(),
        modifier = modifier
            .height(LocalElementSize.current)
            .widthIn(min = LocalElementSize.current)
            .alpha(alpha)
            .clickable(onClick = onItemClick)
            .border(
                width = BorderWidth,
                color = border,
                shape = ButtonsShapeSmall
            )
            .clip(ButtonsShapeSmall)
            .padding(item.additionalPadding())
            .then(
                if (item.useSquare()) Modifier.aspectRatio(1f)
                else Modifier
            )
            .then(
                if (item.useFullSize()) Modifier
                else Modifier.requiredSize(LocalElementSize.current / 2.5f)
            )
    )
}
