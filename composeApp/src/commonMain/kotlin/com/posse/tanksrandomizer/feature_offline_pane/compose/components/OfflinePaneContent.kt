package com.posse.tanksrandomizer.feature_offline_pane.compose.components

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
import com.posse.tanksrandomizer.common.compose.utils.DeviceType.Android
import com.posse.tanksrandomizer.common.compose.utils.LocaleDeviceType
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneEvent
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneState

@Composable
internal fun OfflinePaneContent(
    viewState: OfflinePaneState,
    onEvent: (OfflinePaneEvent) -> Unit,
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

        when (LocaleDeviceType.current) {
            Android -> {
                Spacer(Modifier.height(12.dp))

                SettingsButton(onEvent = onEvent)
            }

            else -> Unit
        }
    }
}