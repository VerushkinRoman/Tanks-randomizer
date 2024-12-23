package com.posse.tanksrandomizer.common.presentation.interactor

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection
import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
import com.posse.tanksrandomizer.common.domain.model.ButtonSize
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

    val floatingButtonSize: StateFlow<ButtonSize>
    fun setFloatingButtonSize(size: ButtonSize)

    fun setAutorotate(autoRotate: Boolean)
    fun setRotation(rotateDirection: RotateDirection)
}