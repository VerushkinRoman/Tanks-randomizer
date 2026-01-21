package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.posse.tanksrandomizer.common.presentation.ads.RewardAdViewModel
import com.posse.tanksrandomizer.common.presentation.ads.RewardAdViewModel.Companion.MAX_ADS_PER_DAY
import com.posse.tanksrandomizer.common.presentation.ads.models.RewardAdEvent
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.viewmodel.rememberViewModel
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.ad_button
import tanks_randomizer.composeapp.generated.resources.ad_description

@Composable
actual fun VerticalScrollbar(
    scrollState: ScrollState,
    modifier: Modifier
) = Unit

@Composable
actual fun BannerAD(
    runningAsOverlay: Boolean,
    modifier: Modifier,
) {
    val viewModel: RewardAdViewModel by rememberViewModel(arg = runningAsOverlay)
    val state by viewModel.viewStates().collectAsStateWithLifecycle()

    Box(
        modifier = modifier
    ) {
        if (state.adAvailable) {
            val context = LocalContext.current

            LaunchedEffect(viewModel) {
                viewModel.obtainEvent(RewardAdEvent.OnScreenStart(context))
            }

            val onShowReward: () -> Unit = remember(viewModel) {
                {
                    viewModel.obtainEvent(RewardAdEvent.ShowRewardedAd(context))
                }
            }

            val adModifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)

            val sizeClass = LocalSizeClass.current

            when (sizeClass) {
                ScreenSize.Small -> ColumnBanner(
                    adCount = state.rewardCount,
                    adEnabled = state.adLoaded,
                    onShowReward = onShowReward,
                    modifier = adModifier
                )

                else -> RowBanner(
                    adCount = state.rewardCount,
                    adEnabled = state.adLoaded,
                    onShowReward = onShowReward,
                    modifier = adModifier
                )
            }
        }
    }
}

@Composable
private fun ColumnBanner(
    adCount: Int,
    adEnabled: Boolean,
    onShowReward: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        BannerCounter(
            adCount = adCount,
            adEnabled = adEnabled,
            onShowReward = onShowReward,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
        )

        Banner(
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun RowBanner(
    adCount: Int,
    adEnabled: Boolean,
    onShowReward: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        BannerCounter(
            adCount = adCount,
            adEnabled = adEnabled,
            onShowReward = onShowReward,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 4.dp),
        )

        Banner(
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun BannerCounter(
    adCount: Int,
    adEnabled: Boolean,
    onShowReward: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        RandomizerText(
            text = stringResource(Res.string.ad_description),
            capitalize = false,
            singleLine = false,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f)
        )

        RandomizerText(
            text = "$adCount/$MAX_ADS_PER_DAY",
            fontSize = 16.sp,
        )

        ADButton(
            onShowReward = onShowReward,
            adEnabled = adEnabled,
        )
    }
}

@Composable
private fun ADButton(
    onShowReward: () -> Unit,
    adEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    SmallButtonWithTextAndImage(
        text = stringResource(Res.string.ad_button),
        painter = rememberVectorPainter(Icons.Rounded.PlayCircleOutline),
        enabled = adEnabled,
        onClick = onShowReward,
        modifier = modifier
    )
}

@Composable
private fun Banner(
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier,
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                BannerAdView(context).apply {
                    setAdUnitId("R-M-18318129-1")
                    setAdSize(
                        BannerAdSize.stickySize(
                            context = context,
                            width = with(density) {
                                this@BoxWithConstraints.maxWidth.toPx().toInt()
                            }
                        )
                    )
                    val requestAd = AdRequest.Builder().build()
                    loadAd(requestAd)
                }
            },
        )
    }
}
