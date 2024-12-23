package com.posse.tanksrandomizer.navigation.compose

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.feature_main_screen.compose.MainScreen

@Composable
fun MainNavigation(
    toggleSettings: () -> Unit = {},
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(
            WindowInsets.safeDrawing
                .only(WindowInsetsSides.Horizontal)
                .asPaddingValues()
        )
) {
    Scaffold(
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.scrim,
        modifier = modifier
    ) {
        MainScreen(
            toggleSettings = toggleSettings,
            modifier = Modifier.fillMaxSize()
        )
    }
}