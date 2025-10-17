package com.posse.tanksrandomizer.feature_settings_pane.presentation

import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.common.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_pane.presentation.model.SettingsState
import com.posse.tanksrandomizer.feature_settings_pane.presentation.use_cases.GetSettingsState

class SettingsViewModel(
    repository: SettingsRepository = Inject.instance(),
    private val settingsInteractor: SettingsInteractor = Inject.instance(),
) : BaseSharedViewModel<SettingsState, SettingsAction, SettingsEvent>(
    initialState = GetSettingsState(repository).invoke()
) {
    override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            SettingsEvent.ClearAction -> viewAction = null
            SettingsEvent.OverlayButtonPressed -> viewAction = SettingsAction.GoToAppSettings
            is SettingsEvent.RotationChanged -> changeRotation(viewEvent.screenRotation)
            is SettingsEvent.SetButtonOpacity -> saveButtonOpacity(viewEvent.opacity)
            is SettingsEvent.SetButtonSize -> saveButtonSize(viewEvent.size)
            is SettingsEvent.FullScreenModeChanged -> changeFullScreen(viewEvent.fullScreen)
        }
    }

    private fun saveButtonOpacity(opacity: Float) {
        viewState = viewState.copy(
            buttonOpacity = opacity
        )

        settingsInteractor.setFloatingButtonOpacity(opacity)
    }

    private fun saveButtonSize(size: Float) {
        viewState = viewState.copy(
            buttonSize = size
        )

        settingsInteractor.setFloatingButtonSize(size)
    }

    private fun changeRotation(screenRotation: ScreenRotation) {
        viewState = viewState.copy(
            screenRotation = screenRotation
        )

        settingsInteractor.setRotation(screenRotation)
    }

    private fun changeFullScreen(fullScreen: Boolean) {
        viewState = viewState.copy(
            fullScreenMode = fullScreen
        )

        settingsInteractor.setFullScreenMode(fullScreen)
    }
}
