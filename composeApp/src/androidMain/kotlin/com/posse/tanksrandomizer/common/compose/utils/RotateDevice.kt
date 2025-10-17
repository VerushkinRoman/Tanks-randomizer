package com.posse.tanksrandomizer.common.compose.utils

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation.Auto
import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation.Landscape
import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation.Portrait
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository

fun Activity.rotateDevice() {
    val repository: SettingsRepository = Inject.instance()
    val rotation = repository.getRotation()

    requestedOrientation = when (rotation) {
        Portrait -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        Landscape -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        Auto -> ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }
}

fun Context.rotateDevice() {
    findActivity()?.rotateDevice()
}
