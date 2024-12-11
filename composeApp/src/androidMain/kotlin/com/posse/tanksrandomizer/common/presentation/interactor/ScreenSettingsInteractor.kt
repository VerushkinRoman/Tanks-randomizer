package com.posse.tanksrandomizer.common.presentation.interactor

import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
import kotlinx.coroutines.flow.StateFlow

interface ScreenSettingsInteractor {
    val windowInFullScreen: StateFlow<Boolean>
    fun setWindowInFullScreen(fullScreen: Boolean)

    val buttonLandscapeOffset: StateFlow<ButtonOffset?>
    fun setButtonLandscapeOffset(offset: ButtonOffset)

    val buttonPortraitOffset: StateFlow<ButtonOffset?>
    fun setButtonPortraitOffset(offset: ButtonOffset)
}