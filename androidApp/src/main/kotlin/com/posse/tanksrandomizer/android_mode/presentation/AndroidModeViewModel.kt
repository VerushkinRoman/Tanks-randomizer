package com.posse.tanksrandomizer.android_mode.presentation

import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeAction
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeState
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor

class AndroidModeViewModel(
    private val settingsInteractor: SettingsInteractor = Inject.instance(),
) : BaseSharedViewModel<AndroidModeState, AndroidModeAction, AndroidModeEvent>(
    initialState = AndroidModeState()
) {
    init {
        val autohide = settingsInteractor.settingsRepository.getAutoHideEnabled()
        val fullScreenModeEnabled = settingsInteractor.fullScreenModeEnabled.value

        when {
            autohide && !fullScreenModeEnabled -> collapseWindow()
            fullScreenModeEnabled -> disableAutohide()
        }
    }

    override fun obtainEvent(viewEvent: AndroidModeEvent) {
        when (viewEvent) {
            AndroidModeEvent.ClearAction -> viewAction = null
            AndroidModeEvent.OnClosePress -> exitApp()
        }
    }

    private fun collapseWindow() {
        settingsInteractor.setWindowInFullScreen(false)
    }

    private fun disableAutohide() {
        settingsInteractor.settingsRepository.setAutohideEnabled(false)
    }

    private fun exitApp() {
        viewAction = AndroidModeAction.ExitApp
    }
}
