package com.posse.tanksrandomizer.compose.main_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.compose.ElementSize
import com.posse.tanksrandomizer.compose.util.getFilterImage
import com.posse.tanksrandomizer.compose.util.getFilterName
import com.posse.tanksrandomizer.presentation.model.MainEvent
import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.repository.model.FilterObjects.Experience
import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus
import com.posse.tanksrandomizer.repository.model.FilterObjects.Level
import com.posse.tanksrandomizer.repository.model.FilterObjects.Nation
import com.posse.tanksrandomizer.repository.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.repository.model.FilterObjects.Status
import com.posse.tanksrandomizer.repository.model.FilterObjects.SwitchItem
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.dice
import tanks_randomizer.composeapp.generated.resources.trash

@Composable
fun FiltersBlock(
    viewState: MainState,
    onEvent: (MainEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val diceClicked = remember { mutableStateOf(false) }

    val allDisabled by remember(viewState) {
        mutableStateOf(
            viewState.levels.all { !it.selected }
                    && viewState.types.all { !it.selected }
                    && viewState.pinned.all { !it.selected }
                    && viewState.nations.all { !it.selected }
                    && viewState.statuses.all { !it.selected }
                    && viewState.experiences.all { !it.selected }
                    && viewState.tankTypes.all { !it.selected }
        )
    }

    val borderColor by animateColorAsState(
        targetValue = if (allDisabled) MaterialTheme.colorScheme.surfaceContainerLowest
        else MaterialTheme.colorScheme.surfaceContainerHigh
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(1.dp, color = borderColor)
            .padding(6.dp)
    ) {
        Filters(
            viewState = viewState,
            onEvent = onEvent,
            diceClicked = diceClicked,
        )

        Spacer(Modifier.height(12.dp))

        Buttons(
            onEvent = onEvent,
            diceClicked = diceClicked,
            allDisabled = allDisabled,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Filters(
    viewState: MainState,
    onEvent: (MainEvent) -> Unit,
    diceClicked: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        FilterItemsRow(
            items = viewState.levels,
            onItemClick = { onEvent(MainEvent.LevelPressed(it as Level)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = viewState.types,
            onItemClick = { onEvent(MainEvent.TypePressed(it as Type)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = viewState.experiences,
            onItemClick = { onEvent(MainEvent.ExperiencePressed(it as Experience)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = viewState.pinned,
            onItemClick = { onEvent(MainEvent.PinnedPressed(it as Pinned)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = viewState.statuses,
            onItemClick = { onEvent(MainEvent.StatusPressed(it as Status)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = viewState.tankTypes,
            onItemClick = { onEvent(MainEvent.TankTypePressed(it as TankType)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = viewState.nations,
            onItemClick = { onEvent(MainEvent.NationPressed(it as Nation)) },
            diceClicked = diceClicked,
        )
    }
}

@Composable
private fun Buttons(
    onEvent: (MainEvent) -> Unit,
    diceClicked: MutableState<Boolean>,
    allDisabled: Boolean,
    modifier: Modifier = Modifier
) {
    val diceColor by animateColorAsState(
        targetValue = if (allDisabled) MaterialTheme.colorScheme.surfaceContainerLowest
        else MaterialTheme.colorScheme.surfaceContainerLow
    )

    val diceAlpha by animateFloatAsState(
        targetValue = if (allDisabled) 0.1f else 1f,
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(ButtonDefaults.MinHeight)
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .clickable { onEvent(MainEvent.TrashFilterPressed) }
                .padding(4.dp)
        ) {
            AnimatedContent(
                targetState = allDisabled
            ) { disabled ->
                if (disabled) {
                    Image(
                        imageVector = Icons.Rounded.DoneAll,
                        contentDescription = stringResource(Res.string.trash),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        imageVector = Icons.Outlined.DeleteForever,
                        contentDescription = stringResource(Res.string.trash),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Image(
            painter = painterResource(Res.drawable.dice),
            contentDescription = stringResource(Res.string.dice),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .size(ButtonDefaults.MinHeight)
                .background(diceColor)
                .clickable(
                    enabled = !allDisabled,
                    onClick = {
                        diceClicked.value = true
                        onEvent(MainEvent.GenerateFilterPressed)
                    }
                )
                .alpha(diceAlpha)
                .padding(6.dp)
        )
    }
}

@Composable
private fun <T : ItemStatus<T>> FilterItemsRow(
    items: List<ItemStatus<T>>,
    onItemClick: (ItemStatus<T>) -> Unit,
    diceClicked: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val switchItem = remember(items) { items.find { it is SwitchItem } }
    val mainItems = remember(items) { items.filter { it !is SwitchItem } }

    val allDisabled by remember(mainItems) { mutableStateOf(mainItems.all { !it.selected }) }

    val borderColor by animateColorAsState(
        targetValue = if (allDisabled) MaterialTheme.colorScheme.surfaceContainerLowest
        else MaterialTheme.colorScheme.surfaceContainerHigh
    )

    Row(
        modifier.height(IntrinsicSize.Min)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            modifier = modifier
                .border(1.dp, color = borderColor)
                .padding(6.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            items.forEach { item ->
                if (item is SwitchItem) return@forEach

                FilterButton(
                    item = item,
                    useColorFilter = item is Level || item is Type || item is Pinned || item is Status,
                    onItemClick = { onItemClick(item) },
                    diceClicked = diceClicked,
                )
            }
        }

        switchItem?.let { itemStatus ->
            @Suppress("UNCHECKED_CAST")
            (itemStatus as? ItemStatus<SwitchItem>)?.let { item ->
                SwitchButton(
                    item = item,
                    allDisabled = allDisabled,
                    onItemClick = { onItemClick(itemStatus) },
                )
            }
        }
    }
}

@Composable
private fun <T> FilterButton(
    item: ItemStatus<T>,
    useColorFilter: Boolean,
    onItemClick: () -> Unit,
    diceClicked: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val alpha by animateFloatAsState(
        targetValue = if (item.selected) 1f else 0.1f,
    )

    val border by animateColorAsState(
        targetValue = when {
            item.random -> {
                if (!diceClicked.value) {
                    Color.Green
                } else {
                    Color.Green.copy(alpha = 0f)
                }
            }

            else -> {
                Color.Transparent
            }
        },
        finishedListener = { diceClicked.value = false },
    )

    val background by animateColorAsState(
        targetValue = if (item.selected) MaterialTheme.colorScheme.surfaceContainerLow
        else MaterialTheme.colorScheme.surfaceContainerLowest
    )

    Image(
        painter = getFilterImage(item),
        contentDescription = getFilterName(item),
        colorFilter = if (useColorFilter) ColorFilter.tint(MaterialTheme.colorScheme.onSurface) else null,
        modifier = modifier
            .height(ElementSize.current + 2.dp)
            .background(background)
            .clickable(onClick = onItemClick)
            .border(1.dp, color = border)
            .alpha(alpha)
            .padding(1.dp)
    )
}

@Composable
private fun SwitchButton(
    item: ItemStatus<SwitchItem>,
    allDisabled: Boolean,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (allDisabled) MaterialTheme.colorScheme.surfaceContainerLowest
        else MaterialTheme.colorScheme.surfaceContainerHigh
    )

    val alpha by animateFloatAsState(
        targetValue = if (allDisabled) 0.3f else 1f,
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(ElementSize.current + 14.dp)
            .background(backgroundColor)
            .clickable(onClick = onItemClick)
            .alpha(alpha)
    ) {
        AnimatedContent(
            targetState = item
        ) {
            Image(
                painter = getFilterImage(it),
                contentDescription = getFilterName(it),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = modifier
                    .size(ButtonDefaults.MinHeight)
                    .padding(4.dp)
            )
        }
    }
}