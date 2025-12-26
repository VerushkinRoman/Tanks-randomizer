package com.posse.tanksrandomizer.android_mode.presentation

import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeAction
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeState
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor

class AndroidModeViewModel(
    val settingsInteractor: SettingsInteractor = Inject.instance(),
) : BaseSharedViewModel<AndroidModeState, AndroidModeAction, AndroidModeEvent>(
    initialState = AndroidModeState()
) {
    override fun obtainEvent(viewEvent: AndroidModeEvent) {
        when (viewEvent) {
            AndroidModeEvent.ClearAction -> viewAction = null
            AndroidModeEvent.OnClosePress -> exitApp()
            AndroidModeEvent.OnStartedAsService -> collapseWindow()
        }
    }

    private fun collapseWindow() {
        settingsInteractor.setWindowInFullScreen(false)
    }

    private fun exitApp() {
        viewAction = AndroidModeAction.ExitApp
    }
}
