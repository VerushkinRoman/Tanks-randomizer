package com.posse.tanksrandomizer.feature_online_screen.compose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.CommonScreenColumn
import com.posse.tanksrandomizer.common.compose.components.AdditionalBottomComponents
import com.posse.tanksrandomizer.feature_offline_screen.compose.components.NumbersBlock
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenEvent
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenState

@Composable
fun OnlineScreenContent(
    viewState: OnlineScreenState,
    onEvent: (OnlineScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    CommonScreenColumn(
        modifier = modifier
    ) {
//        OnlineFiltersBlock(
//            offlineFilters = viewState.offlineFilters,
//            onEvent = onEvent,
//        )

        Spacer(Modifier.height(12.dp))

//        NumbersBlock(
//            numbers = viewState.numbers,
//            onEvent = onEvent,
//        )

        AdditionalBottomComponents(
            onSettingsPressed = { onEvent(OnlineScreenEvent.SettingsPressed) },
            additionalButton = {
                LogOutButton(
                    onClick = { onEvent(OnlineScreenEvent.LogOutPressed) },
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}