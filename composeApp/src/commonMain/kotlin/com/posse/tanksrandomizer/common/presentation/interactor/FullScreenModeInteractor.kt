package com.posse.tanksrandomizer.common.presentation.interactor

import kotlinx.coroutines.flow.StateFlow

interface FullScreenModeInteractor {
    val fullScreenModeEnabled: StateFlow<Boolean>
    fun setFullScreenMode(fullScreen: Boolean)
}