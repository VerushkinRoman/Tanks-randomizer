package com.posse.tanksrandomizer.feature_main_screen.compose.components

import androidx.compose.runtime.Composable

@Composable
expect fun BackButtonExitApp(enabled: () -> Boolean = { true })