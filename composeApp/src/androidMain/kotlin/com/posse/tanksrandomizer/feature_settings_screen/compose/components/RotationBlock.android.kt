package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.posse.tanksrandomizer.common.compose.utils.findActivity
import com.posse.tanksrandomizer.common.compose.utils.rotateDevice
import com.posse.tanksrandomizer.common.presentation.model.Rotation

@Composable
actual fun RotateDevice(rotation: Rotation) {
    val context = LocalContext.current
    LaunchedEffect(rotation) {
        context.findActivity()?.rotateDevice()
    }
}