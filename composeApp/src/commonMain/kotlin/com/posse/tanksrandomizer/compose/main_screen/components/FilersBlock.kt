package com.posse.tanksrandomizer.compose.main_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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

            FilterItem(
                items = viewState.pinned,
                onItemClick = { onEvent(MainEvent.PinnedPressed(it as Pinned)) },
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
                contentDescription = stringResource(Res.string.trash),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(ButtonDefaults.MinHeight)
                    .clickable { onEvent(MainEvent.TrashFilterPressed) }
            )

            Image(
                painter = painterResource(Res.drawable.dice),
                contentDescription = stringResource(Res.string.dice),
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
private fun <T : ItemStatus<T>> FilterItem(
    items: List<ItemStatus<T>>,
    onItemClick: (ItemStatus<T>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        modifier = modifier
            .border(1.dp, color = Color.DarkGray)
            .padding(6.dp)
            .horizontalScroll(rememberScrollState()),
    ) {
        items.forEach { item ->
            FilterButton(
                item = item,
                onItemClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun <T> FilterButton(
    item: ItemStatus<T>,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val alpha by animateFloatAsState(
        targetValue = if (item.selected) 1f else 0.3f,
    )

    val border by animateColorAsState(
        targetValue = if (item.random) Color.Green else Color.Transparent
    )

    Image(
        painter = getFilterImage(item),
        contentDescription = getFilterName(item),
        modifier = modifier
            .height(ElementSize.current + 2.dp)
            .clickable(onClick = onItemClick)
            .border(1.dp, color = border)
            .alpha(alpha)
            .padding(1.dp)
    )
}