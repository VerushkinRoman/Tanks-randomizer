package com.posse.tanksrandomizer.common.core.platform

import android.content.Context

actual class PlatformConfiguration(
    val androidContext: Context,
    val startWindowMode: () -> Unit,
    val startFullScreenMode: () -> Unit,
    val openAppSettings: () -> Unit,
    val canDrawOverlays: () -> Boolean,
    val rotateDevice: () -> Unit,
)
