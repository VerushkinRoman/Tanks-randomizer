package com.posse.tanksrandomizer.compose.main_screen

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.posse.tanksrandomizer.presentation.model.Rotation
import com.posse.tanksrandomizer.utils.RotateDirection.Landscape
import com.posse.tanksrandomizer.utils.RotateDirection.Portrait

@Composable
actual fun RotateDevice(rotation: Rotation) {
    val context = LocalContext.current
    DisposableEffect(rotation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = if (rotation.autoRotateEnabled) {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR
        } else {
            when (rotation.rotateDirection) {
                Portrait -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                Landscape -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }
        }
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}