package com.posse.tanksrandomizer.common.compose.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
}

@Composable
internal actual fun dynamicColorScheme(
    darkTheme: Boolean,
    dynamicColor: Boolean
): ColorScheme? = null