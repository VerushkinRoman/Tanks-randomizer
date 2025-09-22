package com.posse.tanksrandomizer.common.compose.components

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize
import com.posse.tanksrandomizer.common.compose.utils.getFilterImage
import com.posse.tanksrandomizer.common.compose.utils.getFilterName
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem

@Composable
fun <T : ItemStatus<T>> FilterItemsRow(
    items: List<ItemStatus<T>>,
    useColorFilter: Boolean,
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
                    useColorFilter = useColorFilter,
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
