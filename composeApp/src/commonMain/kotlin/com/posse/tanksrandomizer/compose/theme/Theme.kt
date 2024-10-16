package com.posse.tanksrandomizer.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
internal fun AppTheme(
    content: @Composable () -> Unit
) {
    SystemAppearance(false)
    MaterialTheme(
        content = { Surface(content = content) }
    )
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
