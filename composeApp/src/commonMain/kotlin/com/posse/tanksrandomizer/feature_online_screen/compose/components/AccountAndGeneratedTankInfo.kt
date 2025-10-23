package com.posse.tanksrandomizer.feature_online_screen.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.compose.base_components.SmallButtonWithTextAndImage
import com.posse.tanksrandomizer.common.compose.base_components.WidthSizeClassCalculator
import com.posse.tanksrandomizer.feature_online_screen.compose.utils.getFilteredText
import com.posse.tanksrandomizer.feature_online_screen.compose.utils.getRefreshText
import com.posse.tanksrandomizer.feature_online_screen.compose.utils.getTotalText
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
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

        RandomTankBlock(
            generatedTank = generatedTank,
        )
    }
}

@Composable
private fun WideScreen(
    generatedTank: Tank?,
    totalText: String,
    refreshedText: String,
    filteredText: String,
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

        Spacer(Modifier.height(8.dp))

        RandomTankBlock(
            generatedTank = generatedTank,
        )
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
private fun RandomTankBlock(
    generatedTank: Tank?,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        RandomizerText(
            text = stringResource(Res.string.online_random_tank),
        )

        Box(
            modifier = Modifier
                .height(ButtonDefaults.MinHeight * 2)
                .aspectRatio(16 / 9f)
                .background(MaterialTheme.colorScheme.primary)
        )

        RandomizerText(
            text = generatedTank?.name ?: "-",
        )
    }
}
