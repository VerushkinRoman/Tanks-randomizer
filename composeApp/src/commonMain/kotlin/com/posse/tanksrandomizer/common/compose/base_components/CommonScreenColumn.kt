package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.posse.tanksrandomizer.common.compose.utils.horizontalEvenSafeContentPaddings

@Composable
fun CommonScreenColumn(
    modifier: Modifier = Modifier,
    runningAsOverlay: Boolean,
    content: @Composable (ColumnScope.() -> Unit)
) {
    val density = LocalDensity.current
    var additionalSpace: Dp by remember { mutableStateOf(0.dp) }
    var additionalAdSpace by remember { mutableStateOf(0.dp) }

    val screenSize = LocalSizeClass.current
    val horizontal = remember(screenSize) {
        when (screenSize) {
            ScreenSize.Small -> false
            else -> true
        }
    }

    val scrollState = rememberScrollState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Vertical))
                    .padding(
                        horizontal = if (horizontal && runningAsOverlay && additionalSpace <= ButtonDefaults.MinHeight + 16.dp) {
                            maxOf(
                                ButtonDefaults.MinHeight + 16.dp,
                                horizontalEvenSafeContentPaddings
                            )
                        } else horizontalEvenSafeContentPaddings
                    )
                    .padding(vertical = 8.dp)
            ) {
                Spacer(Modifier.weight(1f).onSizeChanged { size ->
                    @Suppress("AssignedValueIsNeverRead", "RedundantSuppression")
                    additionalSpace = with(density) { size.height.toDp() }
                })

                content()

                Spacer(Modifier.weight(1f))

                Spacer(Modifier.height(additionalAdSpace))
            }

            BannerAD(
                runningAsOverlay = runningAsOverlay,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        additionalAdSpace = with(density) { size.height.toDp() }
                    },
            )
        }

        VerticalScrollbar(
            scrollState = scrollState,
            modifier = Modifier.fillMaxHeight(),
        )
    }
}

@Composable
expect fun VerticalScrollbar(
    scrollState: ScrollState,
    modifier: Modifier,
)

@Composable
expect fun BannerAD(
    runningAsOverlay: Boolean,
    modifier: Modifier = Modifier
)
