package com.posse.tanksrandomizer.feature_offline_pane.compose.components

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_offline_pane.compose.util.getFilterImage
import com.posse.tanksrandomizer.feature_offline_pane.compose.util.getFilterName
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineFilters
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneEvent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.dice
import tanks_randomizer.composeapp.generated.resources.trash

@Composable
internal fun FiltersBlock(
    offlineFilters: OfflineFilters,
    onEvent: (OfflinePaneEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val diceClicked = remember { mutableStateOf(false) }

    val allDisabled by remember(offlineFilters) {
        mutableStateOf(
            offlineFilters.levels.all { !it.selected }
                    && offlineFilters.types.all { !it.selected }
                    && offlineFilters.pinned.all { !it.selected }
                    && offlineFilters.nations.all { !it.selected }
                    && offlineFilters.statuses.all { !it.selected }
                    && offlineFilters.experiences.all { !it.selected }
                    && offlineFilters.tankTypes.all { !it.selected }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(1.dp, color = MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(6.dp)
    ) {
        Filters(
            offlineFilters = offlineFilters,
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
    offlineFilters: OfflineFilters,
    onEvent: (OfflinePaneEvent) -> Unit,
    diceClicked: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        FilterItemsRow(
            items = offlineFilters.levels,
            onItemClick = { onEvent(OfflinePaneEvent.LevelPressed(it as Level)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = offlineFilters.types,
            onItemClick = { onEvent(OfflinePaneEvent.TypePressed(it as Type)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = offlineFilters.experiences,
            onItemClick = { onEvent(OfflinePaneEvent.ExperiencePressed(it as Experience)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = offlineFilters.pinned,
            onItemClick = { onEvent(OfflinePaneEvent.PinnedPressed(it as Pinned)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = offlineFilters.statuses,
            onItemClick = { onEvent(OfflinePaneEvent.StatusPressed(it as Status)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = offlineFilters.tankTypes,
            onItemClick = { onEvent(OfflinePaneEvent.TankTypePressed(it as TankType)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = offlineFilters.nations,
            onItemClick = { onEvent(OfflinePaneEvent.NationPressed(it as Nation)) },
            diceClicked = diceClicked,
        )
    }
}

@Composable
private fun Buttons(
    onEvent: (OfflinePaneEvent) -> Unit,
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
                .clickable { onEvent(OfflinePaneEvent.TrashFilterPressed) }
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
                        onEvent(OfflinePaneEvent.GenerateFilterPressed)
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
    val elementSize = LocalElementSize.current
    val density = LocalDensity.current
    var switchItemWidth by remember(elementSize, density) { mutableStateOf(elementSize) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .border(1.dp, color = MaterialTheme.colorScheme.surfaceContainerLow)
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
                    modifier = Modifier.onSizeChanged { size ->
                        val width = with(density) { size.width.toDp() }
                        switchItemWidth = width + 12.dp
                    }
                )
            }
        }

        switchItem?.let { itemStatus ->
            @Suppress("UNCHECKED_CAST")
            (itemStatus as? ItemStatus<SwitchItem>)?.let { item ->
                SwitchButton(
                    item = item,
                    onItemClick = { onItemClick(itemStatus) },
                    modifier = Modifier.width(switchItemWidth)
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
            .height(LocalElementSize.current + 2.dp)
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
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height((LocalElementSize.current / 3).coerceAtLeast(ButtonDefaults.MinHeight / 3))
            .clip(RoundedCornerShape(0, 0, 50, 50))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .clickable(onClick = onItemClick)
            .alpha(0.3f)
    ) {
        AnimatedContent(
            targetState = item
        ) {
            Image(
                painter = getFilterImage(it),
                contentDescription = getFilterName(it),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = modifier
                    .padding(2.dp)
            )
        }
    }
}
