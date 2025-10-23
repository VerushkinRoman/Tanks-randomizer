package com.posse.tanksrandomizer.feature_online_screen.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.CommonScreenColumn
import com.posse.tanksrandomizer.common.compose.base_components.LoadingOverlay
import com.posse.tanksrandomizer.common.compose.base_components.SmallButtonWithTextAndImage
import com.posse.tanksrandomizer.common.compose.components.AdditionalBottomComponents
import com.posse.tanksrandomizer.common.compose.components.FiltersBlock
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenEvent
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.lesta_logo
import tanks_randomizer.composeapp.generated.resources.logout
import kotlin.time.ExperimentalTime

@Composable
fun OnlineScreenContent(
    viewState: OnlineScreenState,
    runningAsOverlay: Boolean,
    onEvent: (OnlineScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val screenAlpha by animateFloatAsState(
        targetValue = if (viewState.loading) 0.3f else 1f
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        MainContent(
            viewState = viewState,
            runningAsOverlay = runningAsOverlay,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = screenAlpha },
        )

        LoadingOverlay(
            visible = viewState.loading,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun MainContent(
    viewState: OnlineScreenState,
    runningAsOverlay: Boolean,
    onEvent: (OnlineScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    CommonScreenColumn(
        runningAsOverlay = runningAsOverlay,
        modifier = modifier
    ) {
        AccountAndGeneratedTankInfo(
            generatedTank = viewState.generatedTank,
            lastAccountUpdated = viewState.lastAccountUpdated,
            tanksOverall = viewState.tanksInGarage.size,
            tanksByFilter = viewState.tanksByFilter.size,
            onRefresh = { onEvent(OnlineScreenEvent.RefreshAccountPressed) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        FiltersBlock(
            components = viewState.onlineFilters.components,
            onFilterItemClick = { onEvent(OnlineScreenEvent.FilterItemChanged(it)) },
            onDiceClick = { onEvent(OnlineScreenEvent.GenerateTankPressed) },
            onTrashClick = { onEvent(OnlineScreenEvent.TrashFilterPressed) },
            onSelectAllClick = { onEvent(OnlineScreenEvent.CheckAllPressed) },
            diceEnabled = viewState.tanksByFilter.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(24.dp))

        AdditionalBottomComponents(
            onSettingsPressed = { onEvent(OnlineScreenEvent.SettingsPressed) },
            additionalButton = {
                LogOutButton(
                    onClick = { onEvent(OnlineScreenEvent.LogOutPressed) },
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun LogOutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SmallButtonWithTextAndImage(
        text = stringResource(Res.string.logout),
        painter = painterResource(Res.drawable.lesta_logo),
        textFirst = false,
        onClick = onClick,
        modifier = modifier
    )
}
