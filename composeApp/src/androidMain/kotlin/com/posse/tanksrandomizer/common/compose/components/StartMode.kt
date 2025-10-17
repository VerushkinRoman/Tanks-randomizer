package com.posse.tanksrandomizer.common.compose.components

import android.content.Context
import android.content.Intent
import android.os.Build
import com.posse.tanksrandomizer.AppActivity
import com.posse.tanksrandomizer.common.compose.utils.findActivity
import com.posse.tanksrandomizer.feature_service.OverlayService

internal fun startWindowMode(context: Context, startedAsService: Boolean) {
    context.findActivity()?.let { activity ->
        Intent(
            /* packageContext = */ context,
            /* cls = */ OverlayService::class.java,
        )
            .apply {
                putExtra(STARTED_AS_SERVICE, startedAsService)
            }
            .also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(it)
                } else {
                    context.startService(it)
                }
            }

        activity.finish()
    }
}

internal fun startFullScreenMode(context: Context) {
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

const val STARTED_AS_SERVICE = "StartedAsService"
