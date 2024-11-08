package com.posse.tanksrandomizer.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.compose.main_screen.MainScreen
import com.posse.tanksrandomizer.compose.theme.AppTheme
import com.posse.tanksrandomizer.compose.util.ElementSize
import com.posse.tanksrandomizer.utils.DataPreloader

@Composable
internal fun App(
    onDataLoaded: () -> Unit = {}
) = AppTheme {
    var dataLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        DataPreloader.getInstance().preloadData {
            onDataLoaded()
            dataLoaded = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim)
    ){
        AnimatedContent(
            targetState = dataLoaded
        ) {
            if (it) {
                BoxWithConstraints {
                    val elementSize: Dp = remember(maxWidth) {
                        val elementWidthByScreen = (maxWidth - (20 * 6).dp) / 12
                        minOf(elementWidthByScreen, ButtonDefaults.MinHeight)
                    }

                    CompositionLocalProvider(
                        ElementSize provides elementSize
                    ) {
                        MainScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp)
                        )
                    }
                }
            }
        }
    }
}