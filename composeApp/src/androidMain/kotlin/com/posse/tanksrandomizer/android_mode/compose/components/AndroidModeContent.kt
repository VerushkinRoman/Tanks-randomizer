package com.posse.tanksrandomizer.android_mode.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeState
import com.posse.tanksrandomizer.common.compose.base_components.BigButtonWithImage
import com.posse.tanksrandomizer.feature_offline_screen.compose.components.BackButtonExitApp
import com.posse.tanksrandomizer.navigation.compose.MainNavigation
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.close_app

@Composable
fun AndroidModeContent(
    state: AndroidModeState,
    startedFromService: Boolean,
    onEvent: (AndroidModeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val startState = remember { state.windowInFullScreen }
    var launched by remember {
        mutableStateOf(startState)
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
        MainNavigation(
            showRotation = !startedFromService,
            runningAsOverlay = startedFromService,
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

@Composable
private fun CloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val safeDrawing = WindowInsets.safeDrawing.asPaddingValues()

    BigButtonWithImage(
        painter = rememberVectorPainter(Icons.Rounded.Close),
        contentDescription = stringResource(Res.string.close_app),
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .padding(
                top = safeDrawing.calculateTopPadding(),
                start = safeDrawing.calculateStartPadding(LocalLayoutDirection.current),
            )
    )
}
