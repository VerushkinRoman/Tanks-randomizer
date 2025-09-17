package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.models.AdditionalBottomComponentsEvent

@Composable
actual fun AdditionalBottomComponents(
    loading: Boolean,
    onAdditionalBottomComponentsEvent: (AdditionalBottomComponentsEvent) -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(Modifier.height(12.dp))

        LogInButton(onClick = {
            onAdditionalBottomComponentsEvent(
                AdditionalBottomComponentsEvent.LogInPressed
            )
        })
    }
}
