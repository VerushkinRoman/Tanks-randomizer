package com.posse.tanksrandomizer.common.compose.utils

import android.app.Activity
import android.content.pm.ActivityInfo
import com.posse.tanksrandomizer.common.compose.utils.RotateDirection.Landscape
import com.posse.tanksrandomizer.common.compose.utils.RotateDirection.Portrait
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository

fun Activity.rotateDevice() {
    val repository: SettingsRepository = Inject.instance()
    val rotation = repository.getRotation()
    val autorotate = repository.getAutorotate()
    requestedOrientation = if (autorotate) {
        ActivityInfo.SCREEN_ORIENTATION_SENSOR
    } else {
        when (rotation) {
            Portrait -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            Landscape -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
    }
}