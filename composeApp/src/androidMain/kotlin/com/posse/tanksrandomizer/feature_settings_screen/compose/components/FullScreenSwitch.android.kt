package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import android.provider.Settings
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration

internal actual fun overlayAvailable(platformConfiguration: PlatformConfiguration): Boolean? {
    return Settings.canDrawOverlays(platformConfiguration.androidContext)
}