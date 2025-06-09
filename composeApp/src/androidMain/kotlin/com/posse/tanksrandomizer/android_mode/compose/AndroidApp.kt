package com.posse.tanksrandomizer.android_mode.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.CommonPlatformApp

@Composable
internal fun AndroidApp(
    startedFromService: Boolean,
    exitApp: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxSize()
) {
    CommonPlatformApp {
        AndroidMode(
            startedFromService = startedFromService,
            exitApp = exitApp,
            modifier = modifier
        )
    }
}