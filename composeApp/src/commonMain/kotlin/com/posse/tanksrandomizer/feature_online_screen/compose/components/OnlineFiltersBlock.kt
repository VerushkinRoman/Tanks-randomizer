package com.posse.tanksrandomizer.feature_online_screen.compose.components

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.components.FilterItemsRow
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenEvent
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineFilters
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenEvent
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenEvent.FilterItemChanged
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.dice
import tanks_randomizer.composeapp.generated.resources.trash

@Composable
internal fun OnlineFiltersBlock(
    onlineFilters: OnlineFilters,
    onEvent: (OnlineScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val diceClicked = remember { mutableStateOf(false) }

    val allDisabled by remember(onlineFilters) {
        mutableStateOf(
            onlineFilters.tiers.all { !it.selected }
                    && onlineFilters.types.all { !it.selected }
                    && onlineFilters.nations.all { !it.selected }
                    && onlineFilters.mastery.all { !it.selected }
                    && onlineFilters.premium.all { !it.selected }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(1.dp, color = MaterialTheme.colorScheme.surfaceContainerLow)
            .padding(6.dp)
    ) {
        Filters(
            onlineFilters = onlineFilters,
            onEvent = onEvent,
            diceClicked = diceClicked,
        )

        Spacer(Modifier.height(12.dp))

//        Buttons(
//            onEvent = onEvent,
//            diceClicked = diceClicked,
//            allDisabled = allDisabled,
//        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Filters(
    onlineFilters: OnlineFilters,
    onEvent: (OnlineScreenEvent) -> Unit,
    diceClicked: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        FilterItemsRow(
            items = onlineFilters.tiers,
            useColorFilter = true,
            onItemClick = { onEvent(FilterItemChanged(item = it as Tier)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = onlineFilters.types,
            useColorFilter = true,
            onItemClick = { onEvent(FilterItemChanged(item = it as Type)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = onlineFilters.mastery,
            useColorFilter = false,
            onItemClick = { onEvent(FilterItemChanged(item = it as Mastery)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = onlineFilters.premium,
            useColorFilter = false,
            onItemClick = { onEvent(FilterItemChanged(item = it as Premium)) },
            diceClicked = diceClicked,
        )

        FilterItemsRow(
            items = onlineFilters.nations,
            useColorFilter = false,
            onItemClick = { onEvent(FilterItemChanged(item = it as Nation)) },
            diceClicked = diceClicked,
        )
    }
}

@Composable
private fun Buttons(
    onEvent: (OfflineScreenEvent) -> Unit,
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
                .clickable { onEvent(OfflineScreenEvent.TrashFilterPressed) }
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
                        onEvent(OfflineScreenEvent.GenerateFilterPressed)
                    }
                )
                .alpha(diceAlpha)
                .padding(6.dp)
        )
    }
}
