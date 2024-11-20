package com.posse.tanksrandomizer.feature_settings_screen.compose

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration

internal actual fun getOverlayPermission(platformConfiguration: PlatformConfiguration) {
    Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        Uri.parse("package:${platformConfiguration.androidContext.packageName}")
    )
        .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
        .also { platformConfiguration.androidContext.startActivity(it) }
}