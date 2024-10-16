package com.posse.tanksrandomizer.compose.main_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.compose.ElementSize
import com.posse.tanksrandomizer.presentation.model.MainEvent
import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.repository.model.Experience
import com.posse.tanksrandomizer.repository.model.ItemInfo
import com.posse.tanksrandomizer.repository.model.Level
import com.posse.tanksrandomizer.repository.model.Nation
import com.posse.tanksrandomizer.repository.model.SingleChoiceObj
import com.posse.tanksrandomizer.repository.model.Status
import com.posse.tanksrandomizer.repository.model.TankType
import com.posse.tanksrandomizer.repository.model.Type
import org.jetbrains.compose.resources.painterResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.dice
import tanks_randomizer.composeapp.generated.resources.trash

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FiltersBlock(
    viewState: MainState,
    onEvent: (MainEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(1.dp, color = Color.DarkGray)
            .padding(6.dp),
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            FilterItem(
                items = viewState.levels,
                onItemClick = { onEvent(MainEvent.LevelPressed(it as Level)) },
            )

            FilterItem(
                items = viewState.types,
                onItemClick = { onEvent(MainEvent.TypePressed(it as Type)) },
            )

            FilterItem(
                items = viewState.experiences,
                onItemClick = { onEvent(MainEvent.ExperiencePressed(it as Experience)) },
            )

            FilterButton(
                item = viewState.pinned,
                onItemClick = { onEvent(MainEvent.PinnedPressed) },
                modifier = Modifier
                    .border(1.dp, color = Color.DarkGray)
                    .padding(6.dp),
            )

            FilterItem(
                items = viewState.statuses,
                onItemClick = { onEvent(MainEvent.StatusPressed(it as Status)) },
            )

            FilterItem(
                items = viewState.tankTypes,
                onItemClick = { onEvent(MainEvent.TankTypePressed(it as TankType)) },
            )

            FilterItem(
                items = viewState.nations,
                onItemClick = { onEvent(MainEvent.NationPressed(it as Nation)) },
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        ) {
            Image(
                painter = painterResource(Res.drawable.trash),
                contentDescription = null,
                modifier = Modifier
                    .height(ButtonDefaults.MinHeight)
                    .clickable { onEvent(MainEvent.TrashFilterPressed) }
            )

            Image(
                painter = painterResource(Res.drawable.dice),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = Modifier
                    .height(ButtonDefaults.MinHeight)
                    .background(Color.DarkGray.copy(alpha = 0.5f))
                    .clickable { onEvent(MainEvent.GenerateFilterPressed) }
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun <T : ItemInfo<T>> FilterItem(
    items: List<ItemInfo<T>>,
    onItemClick: (ItemInfo<T>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        modifier = modifier
            .border(1.dp, color = Color.DarkGray)
            .padding(6.dp),
    ) {
        items.forEach { item ->
            if (item is SingleChoiceObj) {
                SingleChoiceItem(
                    item = item,
                    onItemClick = { onItemClick(item) }
                )
            } else {
                FilterButton(
                    item = item,
                    onItemClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
private fun <T> SingleChoiceItem(
    item: ItemInfo<T>,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val elementHeight = (ElementSize.current - 4.dp) / 3

    AnimatedContent(
        targetState = item.selected,
        modifier = modifier
            .clickable(onClick = onItemClick),
    ) { selected ->
        if (selected) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.size(ElementSize.current)
            ) {
                Box(
                    modifier = Modifier
                        .size(elementHeight)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .size(elementHeight)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = CircleShape
                        )
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )

                Box(
                    modifier = Modifier
                        .size(elementHeight)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.size(ElementSize.current)
            ) {
                val width = with(LocalDensity.current) { 2.dp.toPx() }
                Box(
                    modifier = Modifier
                        .size(elementHeight)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                        )
                        .padding(3.dp)
                        .drawBehind {
                            drawLine(
                                color = Color.Gray,
                                start = Offset.Zero,
                                end = Offset(size.width / 2, size.height),
                                strokeWidth = width,
                            )

                            drawLine(
                                color = Color.Gray,
                                start = Offset(size.width / 2, size.height),
                                end = Offset(size.width, 0f),
                                strokeWidth = width,
                            )
                        }
                )

                Box(
                    modifier = Modifier
                        .size(elementHeight)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                        )
                )

                Box(
                    modifier = Modifier
                        .size(elementHeight)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                        )
                        .padding(3.dp)
                        .drawBehind {
                            drawLine(
                                color = Color.Gray,
                                start = Offset.Zero,
                                end = Offset(size.width / 2, size.height),
                                strokeWidth = width,
                            )

                            drawLine(
                                color = Color.Gray,
                                start = Offset(size.width / 2, size.height),
                                end = Offset(size.width, 0f),
                                strokeWidth = width,
                            )
                        }
                )
            }
        }
    }
}

@Composable
private fun <T> FilterButton(
    item: ItemInfo<T>,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    item.resId?.let { resource ->
        val alpha by animateFloatAsState(
            targetValue = if (item.selected) 1f else 0.3f,
        )

        val border by animateColorAsState(
            targetValue = if (item.random) Color.Green else Color.Transparent
        )

        Image(
            painter = painterResource(resource),
            contentDescription = null,
            modifier = modifier
                .height(ElementSize.current + 2.dp)
                .clickable(onClick = onItemClick)
                .border(1.dp, color = border)
                .alpha(alpha)
                .padding(1.dp)
        )
    }
}