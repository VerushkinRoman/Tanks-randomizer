package com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.posse.tanksrandomizer.common.compose.base_components.LoadingIndicator
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.models.MainScreenEvent
import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.models.MainScreenState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.app_logo
import tanks_randomizer.composeapp.generated.resources.login_description

@Composable
internal fun MainScreenContent(
    viewState: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit,
    modifier: Modifier,
) {
    val screenSize = LocalSizeClass.current

    val useWeight by remember(screenSize) {
        mutableStateOf(screenSize == ScreenSize.Small)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            32.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        AppLogo(
            useWeight = useWeight,
            modifier = Modifier.weight(1f)
        )

        LoginButton(
            loading = viewState.loading,
            onClick = { onEvent(MainScreenEvent.LogIn) },
            modifier = Modifier.fillMaxWidth(),
        )

        LoadingBox(
            useWeight = useWeight,
            loading = viewState.loading,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun LoginButton(
    loading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        MainScreenLogInButton(
            loading = loading,
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(0.7f)
        )

        RandomizerText(
            text = stringResource(Res.string.login_description),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            capitalize = false,
            singleLine = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AppLogo(
    useWeight: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = if (useWeight) modifier else Modifier
    ) {
        Image(
            painter = painterResource(Res.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .heightIn(max = ButtonDefaults.MinHeight * 4)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun LoadingBox(
    useWeight: Boolean,
    loading: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = if (useWeight) modifier else Modifier,
    ) {
        val loadingAlpha by animateFloatAsState(
            targetValue = if (loading) 1f else 0f
        )
        LoadingIndicator(
            modifier = Modifier.graphicsLayer {
                alpha = loadingAlpha
            }
        )
    }
}
