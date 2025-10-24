package com.posse.tanksrandomizer.feature_main_screen.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.LoadingIndicator
import com.posse.tanksrandomizer.common.compose.base_components.WidthSizeClassCalculator
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenEvent
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenState
import org.jetbrains.compose.resources.painterResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.app_logo

@Composable
internal fun MainScreenContent(
    viewState: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit,
    modifier: Modifier,
) {
    WidthSizeClassCalculator(
        modifier = modifier
    ) { sizeClass ->
        val useWeight by remember(sizeClass) {
            mutableStateOf(sizeClass == WindowWidthSizeClass.Compact)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(32.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = if (useWeight) Modifier.weight(1f) else Modifier
            ) {
                AppLogo(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .heightIn(max = ButtonDefaults.MinHeight * 4)
                )
            }

            MainScreenButtons(
                loading = viewState.loading,
                onLoginClick = { onEvent(MainScreenEvent.LogIn) },
                onOfflineClick = { onEvent(MainScreenEvent.ToOfflineScreen) },
            )

            Column(
                modifier = if (useWeight) Modifier.weight(1f) else Modifier
            ) {
                AnimatedVisibility(
                    visible = viewState.loading,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = modifier,
                ) {
                    LoadingIndicator()
                }
            }
        }
    }
}

@Composable
private fun AppLogo(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(Res.drawable.app_logo),
        contentDescription = null,
        modifier = modifier.clip(CircleShape)
    )
}
