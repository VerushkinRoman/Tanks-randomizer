package com.posse.tanksrandomizer.common.compose.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

val LocalMaxWidth = staticCompositionLocalOf<Dp> { error("no default implementation") }
val LocalMaxHeight = staticCompositionLocalOf<Dp> { error("no default implementation") }