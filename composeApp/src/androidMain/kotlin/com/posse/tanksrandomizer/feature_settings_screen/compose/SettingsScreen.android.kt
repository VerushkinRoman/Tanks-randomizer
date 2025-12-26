package com.posse.tanksrandomizer.feature_settings_screen.compose

import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration

actual fun openSettings(platform: PlatformConfiguration) = platform.openAppSettings()
actual fun changeMode(
    platform: PlatformConfiguration,
    fullScreen: Boolean
) {
    if (fullScreen) platform.startFullScreenMode()
    else platform.startWindowMode(false)
}
