package com.posse.tanksrandomizer.android_mode.compose

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.common.compose.CommonPlatformApp
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor

@Composable
internal fun AndroidApp(
    startedFromService: Boolean,
    startedAsService: Boolean,
    exitApp: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxSize()
) {
    val settingsInteractor: SettingsInteractor = remember { Inject.instance() }
    val windowInFullScreen by settingsInteractor.windowInFullScreen.collectAsStateWithLifecycle()
    val fullScreenModeEnabled by settingsInteractor.fullScreenModeEnabled.collectAsStateWithLifecycle()

    val visible by remember(windowInFullScreen, fullScreenModeEnabled) {
        mutableStateOf(windowInFullScreen || fullScreenModeEnabled)
    }

    val appAlpha by animateFloatAsState(
        if (visible) 1f else 0f
    )

    CommonPlatformApp(
        modifier = modifier.graphicsLayer { alpha = appAlpha },
    ) {
        AndroidMode(
            startedFromService = startedFromService,
            startedAsService = startedAsService,
            exitApp = exitApp,
            modifier = Modifier.fillMaxSize()
        )
    }
}
