package com.posse.tanksrandomizer.feature_online_screen.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.posse.tanksrandomizer.common.compose.base_components.LoadingIndicator
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.compose.base_components.SmallButtonWithTextAndImage
import com.posse.tanksrandomizer.common.compose.base_components.WidthSizeClassCalculator
import com.posse.tanksrandomizer.feature_online_screen.compose.utils.getFilteredText
import com.posse.tanksrandomizer.feature_online_screen.compose.utils.getRefreshText
import com.posse.tanksrandomizer.feature_online_screen.compose.utils.getTotalText
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.app_logo
import tanks_randomizer.composeapp.generated.resources.online_random_tank
import tanks_randomizer.composeapp.generated.resources.online_tanks_refresh
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun AccountAndGeneratedTankInfo(
    generatedTank: Tank?,
    lastAccountUpdated: Instant?,
    tanksOverall: Int,
    tanksByFilter: Int,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val totalText = getTotalText(tanksOverall)
    val refreshedText = getRefreshText(lastAccountUpdated)
    val filteredText = getFilteredText(tanksByFilter)
    val tankName = generatedTank?.name ?: "-"

    WidthSizeClassCalculator(
        modifier = modifier
    ) { widthSizeClass ->
        when (widthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                CompactScreen(
                    generatedTank = generatedTank,
                    totalText = totalText,
                    refreshedText = refreshedText,
                    filteredText = filteredText,
                    tankName = tankName,
                    onRefresh = onRefresh,
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                WideScreen(
                    generatedTank = generatedTank,
                    totalText = totalText,
                    refreshedText = refreshedText,
                    filteredText = filteredText,
                    tankName = tankName,
                    onRefresh = onRefresh,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun CompactScreen(
    generatedTank: Tank?,
    totalText: String,
    refreshedText: String,
    filteredText: String,
    tankName: String,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        RandomizerText(
            text = totalText,
        )

        RandomizerText(
            text = refreshedText,
            fontSize = 8.sp
        )

        Spacer(Modifier.height(8.dp))

        RefreshButton(
            onRefresh = onRefresh,
        )

        Spacer(Modifier.height(8.dp))

        RandomizerText(
            text = filteredText,
        )

        Spacer(Modifier.height(8.dp))

        RandomizerText(
            text = stringResource(Res.string.online_random_tank),
        )

        Spacer(Modifier.height(4.dp))

        RandomTankImage(
            generatedTank = generatedTank,
        )

        Spacer(Modifier.height(4.dp))

        RandomizerText(
            text = tankName,
            capitalize = false,
        )
    }
}

@Composable
private fun WideScreen(
    generatedTank: Tank?,
    totalText: String,
    refreshedText: String,
    filteredText: String,
    tankName: String,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(1f),
            ) {
                RandomizerText(
                    text = totalText,
                )
                RandomizerText(
                    text = refreshedText,
                    fontSize = 8.sp,
                )
            }

            Spacer(Modifier.width(16.dp))

            RefreshButton(
                onRefresh = onRefresh,
            )

            Spacer(Modifier.width(16.dp))

            RandomizerText(
                text = filteredText,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.weight(1f),
            ) {
                RandomizerText(
                    text = stringResource(Res.string.online_random_tank),
                )
            }

            Spacer(Modifier.width(16.dp))

            RandomTankImage(
                generatedTank = generatedTank,
            )

            Spacer(Modifier.width(16.dp))

            RandomizerText(
                text = tankName,
                capitalize = false,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun RefreshButton(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SmallButtonWithTextAndImage(
        text = stringResource(Res.string.online_tanks_refresh),
        painter = rememberVectorPainter(Icons.Rounded.Autorenew),
        textFirst = false,
        onClick = onRefresh,
        modifier = modifier,
    )
}

@Composable
private fun RandomTankImage(
    generatedTank: Tank?,
    modifier: Modifier = Modifier
) {
    var loading by remember { mutableStateOf(false) }
    val loadingAlpha by animateFloatAsState(
        targetValue = if (loading) 0f else 1f
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        AsyncImage(
            model = generatedTank?.imageUrl,
            contentDescription = generatedTank?.name,
            contentScale = ContentScale.FillHeight,
            placeholder = if (generatedTank == null && !loading) painterResource(Res.drawable.app_logo) else null,
            error = painterResource(Res.drawable.app_logo),
            onLoading = { loading = true },
            onSuccess = { loading = false },
            onError = { loading = false },
            modifier = Modifier
                .height(ButtonDefaults.MinHeight * 2)
                .width(ButtonDefaults.MinHeight * 3)
                .graphicsLayer { alpha = loadingAlpha }
        )

        if (loading) {
            LoadingIndicator()
        }
    }
}
