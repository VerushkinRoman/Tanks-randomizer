package com.posse.tanksrandomizer.common.presentation.interactor

import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
import kotlinx.coroutines.flow.StateFlow

interface ScreenSettingsInteractor {
    val windowInFullScreen: StateFlow<Boolean>
    val buttonOffset: StateFlow<ButtonOffset?>
    fun setWindowInFullScreen(fullScreen: Boolean)
    fun setButtonOffset(offset: ButtonOffset)
}