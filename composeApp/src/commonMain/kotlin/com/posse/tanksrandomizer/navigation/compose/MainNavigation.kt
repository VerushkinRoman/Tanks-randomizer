package com.posse.tanksrandomizer.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.posse.tanksrandomizer.common.compose.utils.getScreenSize
import com.posse.tanksrandomizer.navigation.compose.components.MultipleLargeScreenNavigation
import com.posse.tanksrandomizer.navigation.compose.components.SingleMediumScreenNavigation
import com.posse.tanksrandomizer.navigation.compose.components.SingleSmallScreenNavigation

@Composable
fun MainNavigation(
    runningAsOverlay: Boolean = false,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        when (getScreenSize()) {
            ScreenSize.Small -> SingleSmallScreenNavigation(
                runningAsOverlay = runningAsOverlay,
                snackbarHostState = snackbarHostState,
                modifier = Modifier.fillMaxSize(),
            )

            ScreenSize.Medium -> SingleMediumScreenNavigation(
                runningAsOverlay = runningAsOverlay,
                snackbarHostState = snackbarHostState,
                modifier = Modifier.fillMaxSize(),
            )

            ScreenSize.Large -> MultipleLargeScreenNavigation(
                runningAsOverlay = runningAsOverlay,
                snackbarHostState = snackbarHostState,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
