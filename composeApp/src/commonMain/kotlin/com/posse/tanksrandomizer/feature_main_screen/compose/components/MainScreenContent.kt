package com.posse.tanksrandomizer.feature_main_screen.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.LoadingIndicator
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenEvent
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenState
import org.jetbrains.compose.resources.painterResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.app_icon

@Composable
internal fun MainScreenContent(
    viewState: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            AppLogo(modifier = Modifier.fillMaxWidth(0.6f))
        }

        MainScreenButtons(
            loading = viewState.loading,
            onLoginClick = { onEvent(MainScreenEvent.LogIn) },
            onOfflineClick = { onEvent(MainScreenEvent.ToOfflineScreen) },
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            LoadingIndicator(loading = viewState.loading)
        }
    }
}

@Composable
private fun AppLogo(
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clip(CircleShape)
    ) {
        Image(
            painter = painterResource(Res.drawable.app_icon),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val scale = 1.4f
                    scaleX = scale
                    scaleY = scale
                }
        )
    }
}
