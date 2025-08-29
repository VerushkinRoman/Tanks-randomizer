package com.posse.tanksrandomizer.common.compose.utils

import androidx.compose.runtime.staticCompositionLocalOf

enum class DeviceType {
    Android,
    Ios,
    Desktop
}

val LocalDeviceType = staticCompositionLocalOf<DeviceType> { error("no default implementation") }