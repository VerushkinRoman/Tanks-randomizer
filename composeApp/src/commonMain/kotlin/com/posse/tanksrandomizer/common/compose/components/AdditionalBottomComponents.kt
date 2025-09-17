package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.common.compose.models.AdditionalBottomComponentsEvent

@Composable
expect fun AdditionalBottomComponents(
    loading: Boolean,
    onAdditionalBottomComponentsEvent: (AdditionalBottomComponentsEvent) -> Unit,
    modifier: Modifier = Modifier,
)
