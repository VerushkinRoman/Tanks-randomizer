package com.posse.tanksrandomizer.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.compose.main_screen.MainScreen
import com.posse.tanksrandomizer.compose.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    BoxWithConstraints {
        val elementSize: Dp = remember(maxWidth) {
            val elementWidthByScreen = (maxWidth - (20 * 6).dp) / 15
            minOf(elementWidthByScreen, ButtonDefaults.MinHeight)
        }

        CompositionLocalProvider(
            ElementSize provides elementSize
        ) {
            MainScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.scrim)
                    .padding(6.dp)
            )
        }
    }
}

val ElementSize = staticCompositionLocalOf<Dp> { error("no default implementation") }