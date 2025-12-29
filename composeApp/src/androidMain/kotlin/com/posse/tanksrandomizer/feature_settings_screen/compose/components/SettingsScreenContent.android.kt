package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest

actual val rotationAvailable: Boolean = true
actual val overlayAvailable: Boolean = true

@Composable
actual fun BannerAD(
    modifier: Modifier,
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val sizeClass = LocalSizeClass.current

    val banners = remember {
        buildList {
            add(BannerAdView(context))
            when (sizeClass) {
                ScreenSize.Small -> Unit
                else -> add(BannerAdView(context))
            }
        }
    }

    BoxWithConstraints(
        modifier = modifier,
    ) {
        @Suppress("COMPOSE_APPLIER_CALL_MISMATCH")
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            banners.forEach { banner ->
                AndroidView(
                    modifier = Modifier.weight(1f),
                    factory = { context ->
                        banner.apply {
                            setAdUnitId("R-M-18318129-1")
                            setAdSize(
                                BannerAdSize.stickySize(
                                    context = context,
                                    width = with(density) {
                                        this@BoxWithConstraints.maxWidth.toPx()
                                            .toInt() / banners.size
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
    }
}
