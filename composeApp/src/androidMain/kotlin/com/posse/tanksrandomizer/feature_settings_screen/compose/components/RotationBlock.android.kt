package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.posse.tanksrandomizer.common.compose.utils.RotateDirection.Landscape
import com.posse.tanksrandomizer.common.compose.utils.RotateDirection.Portrait
import com.posse.tanksrandomizer.common.compose.utils.findActivity
import com.posse.tanksrandomizer.common.presentation.model.Rotation

@Composable
actual fun RotateDevice(rotation: Rotation) {
    val context = LocalContext.current
    LaunchedEffect(rotation) {
        val activity = context.findActivity() ?: return@LaunchedEffect
        activity.requestedOrientation = if (rotation.autoRotateEnabled) {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR
        } else {
            when (rotation.rotateDirection) {
                Portrait -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                Landscape -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }
        }
    }
}