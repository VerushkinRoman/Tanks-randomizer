package com.posse.tanksrandomizer.common.compose.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
    val view = LocalView.current
    LaunchedEffect(isDark) {
        (view.context as? Activity)?.window?.let { window ->
            WindowInsetsControllerCompat(window, window.decorView).apply {
                isAppearanceLightStatusBars = isDark
                isAppearanceLightNavigationBars = isDark
            }
        }
    }
}

@Composable
internal actual fun dynamicColorScheme(
    darkTheme: Boolean,
    dynamicColor: Boolean
): ColorScheme? {
    return if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else null
}

@Composable
fun themedSegmentedButtonColors() = SegmentedButtonDefaults.colors(
    activeContainerColor = MaterialTheme.colorScheme.primary,
    activeContentColor = MaterialTheme.colorScheme.onSurface,
    activeBorderColor = MaterialTheme.colorScheme.onSurface,
    inactiveContainerColor = MaterialTheme.colorScheme.primary,
    inactiveContentColor = MaterialTheme.colorScheme.onSurface,
    inactiveBorderColor = MaterialTheme.colorScheme.onSurface,
    disabledActiveContainerColor = MaterialTheme.colorScheme.primary,
    disabledActiveContentColor = MaterialTheme.colorScheme.onSurface,
    disabledActiveBorderColor = MaterialTheme.colorScheme.onSurface,
    disabledInactiveContainerColor = MaterialTheme.colorScheme.primary,
    disabledInactiveContentColor = MaterialTheme.colorScheme.onSurface,
    disabledInactiveBorderColor = MaterialTheme.colorScheme.onSurface,
)
