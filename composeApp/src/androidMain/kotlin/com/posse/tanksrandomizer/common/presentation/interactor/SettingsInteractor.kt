package com.posse.tanksrandomizer.common.presentation.interactor

import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation
import com.posse.tanksrandomizer.common.domain.models.ButtonOffset
import kotlinx.coroutines.flow.StateFlow

interface SettingsInteractor {
    val windowInFullScreen: StateFlow<Boolean>
    fun setWindowInFullScreen(fullScreen: Boolean)

    val buttonLandscapeOffset: StateFlow<ButtonOffset?>
    fun setButtonLandscapeOffset(offset: ButtonOffset)

    val buttonPortraitOffset: StateFlow<ButtonOffset?>
    fun setButtonPortraitOffset(offset: ButtonOffset)

    val fullScreenModeEnabled: StateFlow<Boolean>
    fun setFullScreenMode(fullScreen: Boolean)

    val floatingButtonOpacity: StateFlow<Float>
    fun setFloatingButtonOpacity(opacity: Float)

    val floatingButtonSize: StateFlow<Float>
    fun setFloatingButtonSize(size: Float)

    fun setRotation(screenRotation: ScreenRotation)
}
