package com.posse.tanksrandomizer.feature_main_screen.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.components.LogInButton
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenEvent
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.app_icon
import tanks_randomizer.composeapp.generated.resources.login_offline

@Composable
fun MainScreenContent(
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
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.width(IntrinsicSize.Max),
        ) {
            LogInButton(
                loading = viewState.loading,
                onClick = { onEvent(MainScreenEvent.LogIn) },
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(
                onClick = { onEvent(MainScreenEvent.ToOfflineScreen) },
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(Res.string.login_offline),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
