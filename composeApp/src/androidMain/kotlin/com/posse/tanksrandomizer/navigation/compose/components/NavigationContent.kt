package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.feature_main_screen.compose.components.BackButtonExitApp
import com.posse.tanksrandomizer.navigation.compose.MainScaffoldWithBottomSheet
import com.posse.tanksrandomizer.navigation.presentation.model.NavigationEvent
import com.posse.tanksrandomizer.navigation.presentation.model.NavigationState

@Composable
fun NavigationContent(
    state: NavigationState,
    startedFromService: Boolean,
    onEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                WindowInsets.safeDrawing
                    .only(WindowInsetsSides.Horizontal)
                    .asPaddingValues()
            )
    ) {
        MainScaffoldWithBottomSheet(
            windowInFullScreen = state.windowInFullScreen,
            showRotation = !startedFromService,
            modifier = Modifier
        )

        CloseButton(
            windowInFullScreen = state.windowInFullScreen,
            startedFromService = startedFromService,
            onClick = { onEvent(NavigationEvent.OnClosePress) },
            modifier = Modifier.align(Alignment.TopStart)
        )
    }

    BackButtonExitApp()
}