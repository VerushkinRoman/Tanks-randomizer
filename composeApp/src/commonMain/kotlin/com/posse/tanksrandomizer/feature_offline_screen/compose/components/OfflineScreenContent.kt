package com.posse.tanksrandomizer.feature_offline_screen.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenEvent
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.lesta_logo
import tanks_randomizer.composeapp.generated.resources.login_online

@Composable
internal fun OfflineScreenContent(
    viewState: OfflineScreenState,
    runningAsOverlay: Boolean,
    onEvent: (OfflineScreenEvent) -> Unit,
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

        AnimatedVisibility(
            visible = viewState.loading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = modifier,
        ) {
            LoadingOverlay(
                visible = viewState.loading,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun MainContent(
    viewState: OfflineScreenState,
    runningAsOverlay: Boolean,
    onEvent: (OfflineScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    CommonScreenColumn(
        runningAsOverlay = runningAsOverlay,
        modifier = modifier
    ) {
        FiltersBlock(
            components = viewState.offlineFilters.components,
            onFilterItemClick = { onEvent(OfflineScreenEvent.FilterItemChanged(it)) },
            onDiceClick = { onEvent(OfflineScreenEvent.GenerateFilterPressed) },
            onTrashClick = { onEvent(OfflineScreenEvent.TrashFilterPressed) },
            onSelectAllClick = { onEvent(OfflineScreenEvent.CheckAllPressed) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(24.dp))

        NumbersBlock(
            numbers = viewState.numbers,
            onEvent = onEvent,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(24.dp))

        AdditionalBottomComponents(
            onSettingsPressed = { onEvent(OfflineScreenEvent.SettingsPressed) },
            additionalButton = {
                OfflineScreenLogInButton(
                    onClick = { onEvent(OfflineScreenEvent.LogInPressed) },
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun OfflineScreenLogInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SmallButtonWithTextAndImage(
        text = stringResource(Res.string.login_online),
        painter = painterResource(Res.drawable.lesta_logo),
        textFirst = false,
        onClick = onClick,
        modifier = modifier
    )
}
