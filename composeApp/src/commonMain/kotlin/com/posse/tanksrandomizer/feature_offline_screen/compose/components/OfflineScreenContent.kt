package com.posse.tanksrandomizer.feature_offline_screen.compose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.CommonScreenColumn
import com.posse.tanksrandomizer.common.compose.components.AdditionalBottomComponents
import com.posse.tanksrandomizer.common.compose.components.LogInButton
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenEvent
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState

@Composable
internal fun OfflineScreenContent(
    viewState: OfflineScreenState,
    onEvent: (OfflineScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    CommonScreenColumn(
        modifier = modifier
    ) {
        OfflineFiltersBlock(
            offlineFilters = viewState.offlineFilters,
            onEvent = onEvent,
        )

        Spacer(Modifier.height(12.dp))

        NumbersBlock(
            numbers = viewState.numbers,
            onEvent = onEvent,
        )

        AdditionalBottomComponents(
            onSettingsPressed = { onEvent(OfflineScreenEvent.SettingsPressed) },
            additionalButton = {
                LogInButton(
                    loading = viewState.loading,
                    onClick = { onEvent(OfflineScreenEvent.LogInPressed) },
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
