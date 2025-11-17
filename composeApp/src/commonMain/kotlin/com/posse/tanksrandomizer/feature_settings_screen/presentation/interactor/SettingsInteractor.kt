package com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor

import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ButtonOffset
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation
import kotlinx.coroutines.flow.StateFlow

interface SettingsInteractor {
    val screenRotation: StateFlow<ScreenRotation>
    fun setScreenRotation(rotation: ScreenRotation)

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

    fun getDesktopWindowSize(): Pair<Int, Int>?
    fun setDesktopWindowSize(size: Pair<Int, Int>)
}
