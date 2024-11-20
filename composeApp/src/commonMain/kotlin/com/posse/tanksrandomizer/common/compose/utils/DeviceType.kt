package com.posse.tanksrandomizer.common.compose.utils

import androidx.compose.runtime.staticCompositionLocalOf

enum class DeviceType {
    Android,
    Ios,
    Desktop
}

val LocaleDeviceType = staticCompositionLocalOf<DeviceType> { error("no default implementation") }