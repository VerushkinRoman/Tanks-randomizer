package com.posse.tanksrandomizer.android_mode.compose.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import com.posse.tanksrandomizer.feature_offline_pane.compose.components.BackButtonExitApp
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeState

@Composable
fun AndroidModeContent(
    state: AndroidModeState,
    startedFromService: Boolean,
    onEvent: (AndroidModeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val startState = remember { state.windowInFullScreen }
    val configuration = LocalConfiguration.current
    var launched by remember {
        mutableStateOf(
            startState
                    && (!startedFromService || configuration.orientation != Configuration.ORIENTATION_PORTRAIT)
        )
    }

    LaunchedEffect(state.windowInFullScreen) {
        if (startState != state.windowInFullScreen) {
            launched = true
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (launched && state.windowInFullScreen) 1f else 0f
    )

    Box(
        modifier = modifier
            .alpha(alpha)
            .padding(
                WindowInsets.safeDrawing
                    .only(WindowInsetsSides.Horizontal)
                    .asPaddingValues()
            )
    ) {
        MainScaffoldWithBottomSheet(
            showRotation = !startedFromService,
            showFloatingButtonSettings = startedFromService,
            modifier = Modifier
        )

        if (startedFromService) {
            CloseButton(
                onClick = { onEvent(AndroidModeEvent.OnClosePress) },
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
    }

    BackButtonExitApp()
}