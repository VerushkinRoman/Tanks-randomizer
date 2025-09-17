package com.posse.tanksrandomizer.feature_offline_screen.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.components.AdditionalBottomComponents
import com.posse.tanksrandomizer.common.compose.models.AdditionalBottomComponentsEvent
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenEvent
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState

@Composable
internal fun OfflineScreenContent(
    viewState: OfflineScreenState,
    onEvent: (OfflineScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(6.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Vertical))
    ) {
        FiltersBlock(
            offlineFilters = viewState.offlineFilters,
            onEvent = onEvent,
        )

        Spacer(Modifier.height(12.dp))

        NumbersBlock(
            numbers = viewState.numbers,
            onEvent = onEvent,
        )

        AdditionalBottomComponents(
            loading = viewState.loading,
            onAdditionalBottomComponentsEvent = { event ->
                when (event) {
                    AdditionalBottomComponentsEvent.LogInPressed -> onEvent(OfflineScreenEvent.LogInPressed)
                    AdditionalBottomComponentsEvent.SettingsPressed -> onEvent(OfflineScreenEvent.SettingsPressed)
                }
            },
        )
    }
}
