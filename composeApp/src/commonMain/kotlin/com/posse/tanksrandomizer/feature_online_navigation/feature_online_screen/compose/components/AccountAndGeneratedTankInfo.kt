package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.posse.tanksrandomizer.common.compose.base_components.ButtonWithImage
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeSmall
import com.posse.tanksrandomizer.common.compose.base_components.LoadingIndicator
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.compose.base_components.SmallButtonWithTextAndImage
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.utils.getFilteredText
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.utils.getRefreshText
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.utils.getTotalText
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.app_logo_monochrome
import tanks_randomizer.composeapp.generated.resources.online_random_tank
import tanks_randomizer.composeapp.generated.resources.online_tanks_refresh
import tanks_randomizer.composeapp.generated.resources.online_tooltip_button
import tanks_randomizer.composeapp.generated.resources.online_tooltip_content_description
import tanks_randomizer.composeapp.generated.resources.online_tooltip_text
import tanks_randomizer.composeapp.generated.resources.online_tooltip_title
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun AccountAndGeneratedTankInfo(
    generatedTank: Tank?,
    lastAccountUpdated: Instant?,
    tanksOverall: Int,
    tanksByFilter: Int,
    loading: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val totalText = getTotalText(tanksOverall)
    val refreshedText = getRefreshText(lastAccountUpdated)
    val filteredText = getFilteredText(tanksByFilter)
    val tankName = generatedTank?.name ?: "-"

    when (LocalSizeClass.current) {
        ScreenSize.Small -> {
            CompactScreen(
                generatedTank = generatedTank,
                totalText = totalText,
                refreshedText = refreshedText,
                filteredText = filteredText,
                tankName = tankName,
                loading = loading,
                onRefresh = onRefresh,
                modifier = modifier
            )
        }

        else -> {
            WideScreen(
                generatedTank = generatedTank,
                totalText = totalText,
                refreshedText = refreshedText,
                filteredText = filteredText,
                tankName = tankName,
                loading = loading,
                onRefresh = onRefresh,
                modifier = modifier
            )
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
    loading: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        TotalTanks(
            totalText = totalText,
        )

        RandomizerText(
            text = refreshedText,
            fontSize = 8.sp
        )

        Spacer(Modifier.height(8.dp))

        RefreshButton(
            onRefresh = onRefresh,
            loading = loading,
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
    loading: Boolean,
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
                TotalTanks(
                    totalText = totalText,
                )

                RandomizerText(
                    text = refreshedText,
                    fontSize = 8.sp,
                )
            }

            Spacer(Modifier.width(16.dp))

            RefreshButton(
                onRefresh = onRefresh,
                loading = loading,
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
    loading: Boolean,
    modifier: Modifier = Modifier,
) {
    val loadingAlpha by animateFloatAsState(
        targetValue = if (loading) 1f else 0f
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        SmallButtonWithTextAndImage(
            text = stringResource(Res.string.online_tanks_refresh),
            painter = rememberVectorPainter(Icons.Rounded.Autorenew),
            textFirst = false,
            onClick = onRefresh,
            enabled = !loading,
            modifier = Modifier.graphicsLayer { alpha = 1 - loadingAlpha },
        )

        LoadingIndicator(
            Modifier.graphicsLayer { alpha = loadingAlpha }
        )
    }
}

@Composable
private fun RandomTankImage(
    generatedTank: Tank?,
    modifier: Modifier = Modifier
) {
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    val loadingAlpha by animateFloatAsState(
        targetValue = if (loading) 0f else 1f
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        val emptyTank by remember(generatedTank, loading) {
            mutableStateOf(generatedTank == null && !loading)
        }

        AsyncImage(
            model = generatedTank?.imageUrl,
            contentDescription = generatedTank?.name,
            contentScale = ContentScale.FillHeight,
            placeholder = if (emptyTank) painterResource(Res.drawable.app_logo_monochrome) else null,
            error = painterResource(Res.drawable.app_logo_monochrome),
            onLoading = {
                error = false
                loading = true
            },
            onSuccess = {
                error = false
                loading = false
            },
            onError = {
                error = true
                loading = false
            },
            colorFilter = if (emptyTank || generatedTank?.imageUrl == null || error) ColorFilter.tint(
                MaterialTheme.colorScheme.primary
            ) else null,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TotalTanks(
    totalText: String,
    modifier: Modifier = Modifier
) {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val coroutineScope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        RandomizerText(
            text = totalText
        )

        Spacer(Modifier.width(4.dp))

        TooltipBox(
            state = tooltipState,
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                positioning = TooltipAnchorPosition.Below,
                spacingBetweenTooltipAndAnchor = 8.dp
            ),
            onDismissRequest = {
                coroutineScope.launch {
                    tooltipState.dismiss()
                }
            },
            hasAction = true,
            enableUserInput = false,
            tooltip = {
                RichTooltip(
                    shape = ButtonsShapeSmall,
                    colors = TooltipDefaults.richTooltipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    title = {
                        RandomizerText(
                            text = stringResource(Res.string.online_tooltip_title),
                        )
                    },
                    action = {
                        SmallButtonWithTextAndImage(
                            text = stringResource(Res.string.online_tooltip_button),
                            onClick = {
                                coroutineScope.launch {
                                    tooltipState.dismiss()
                                }
                            },
                        )
                    },
                ) {
                    RandomizerText(
                        text = stringResource(Res.string.online_tooltip_text),
                        capitalize = false,
                        singleLine = false,
                    )
                }
            },
        ) {
            ButtonWithImage(
                painter = rememberVectorPainter(Icons.Rounded.Info),
                contentDescription = stringResource(Res.string.online_tooltip_content_description),
                isBig = false,
                onClick = {
                    coroutineScope.launch {
                        tooltipState.show()
                    }
                },
                modifier = Modifier.size(ButtonDefaults.MinHeight / 3)
            )
        }
    }
}
