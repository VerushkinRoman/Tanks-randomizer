package com.posse.tanksrandomizer.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.ScreenSize
import com.posse.tanksrandomizer.navigation.compose.components.MultipleLargeScreen
import com.posse.tanksrandomizer.navigation.compose.components.SingleScreenNavigation

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
        when (LocalSizeClass.current) {
            ScreenSize.Large -> MultipleLargeScreen(
                runningAsOverlay = runningAsOverlay,
                snackbarHostState = snackbarHostState,
                modifier = Modifier.fillMaxSize(),
            )

            else -> SingleScreenNavigation(
                runningAsOverlay = runningAsOverlay,
                snackbarHostState = snackbarHostState,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
