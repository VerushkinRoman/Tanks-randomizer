package com.posse.tanksrandomizer.common.compose.components

import android.content.Intent
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.posse.tanksrandomizer.AppActivity
import com.posse.tanksrandomizer.common.compose.utils.findActivity
import com.posse.tanksrandomizer.feature_service.OverlayService

@Composable
internal fun StartWindowMode() {
    val context = LocalContext.current

    context.findActivity()?.let { activity ->
        Intent(
            /* packageContext = */ context,
            /* cls = */ OverlayService::class.java,
        ).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(it)
            } else {
                context.startService(it)
            }
        }

        activity.finish()
    }
}

@Composable
internal fun StartFullScreenMode() {
    val context = LocalContext.current

    if (context is OverlayService) {
        Intent(
            /* packageContext = */ context,
            /* cls = */ AppActivity::class.java
        )
            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
            .also { context.startActivity(it) }

        context.stopService()
    }
}