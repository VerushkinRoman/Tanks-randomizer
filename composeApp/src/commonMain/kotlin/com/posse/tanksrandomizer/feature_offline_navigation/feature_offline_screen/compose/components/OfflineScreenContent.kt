package com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.compose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.CommonScreenColumn
import com.posse.tanksrandomizer.common.compose.components.FiltersBlock
import com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.presentation.models.OfflineScreenEvent
import com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.presentation.models.OfflineScreenState

@Composable
internal fun OfflineScreenContent(
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
    }
}
