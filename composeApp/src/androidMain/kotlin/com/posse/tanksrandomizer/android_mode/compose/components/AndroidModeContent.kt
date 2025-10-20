package com.posse.tanksrandomizer.android_mode.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent
import com.posse.tanksrandomizer.common.compose.base_components.BigButtonWithImage
import com.posse.tanksrandomizer.common.compose.utils.getHorizontalEvenSafeContentPaddings
import com.posse.tanksrandomizer.feature_offline_screen.compose.components.BackButtonExitApp
import com.posse.tanksrandomizer.navigation.compose.MainNavigation
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.close_app

@Composable
fun AndroidModeContent(
    startedFromService: Boolean,
    onEvent: (AndroidModeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
    ) {
        MainNavigation(
            showRotation = !startedFromService,
            runningAsOverlay = startedFromService,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = getHorizontalEvenSafeContentPaddings())
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
