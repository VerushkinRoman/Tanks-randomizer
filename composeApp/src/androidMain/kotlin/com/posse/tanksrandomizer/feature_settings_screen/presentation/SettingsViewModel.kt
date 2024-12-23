package com.posse.tanksrandomizer.feature_settings_screen.presentation

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.model.ButtonSize
import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.common.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsState
import com.posse.tanksrandomizer.feature_settings_screen.presentation.use_cases.GetSettingsState

class SettingsViewModel(
    repository: SettingsRepository = Inject.instance(),
    private val settingsInteractor: SettingsInteractor = Inject.instance(),
) : BaseSharedViewModel<SettingsState, SettingsAction, SettingsEvent>(
    initialState = GetSettingsState(repository).invoke()
) {
    override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            SettingsEvent.ClearAction -> viewAction = null
            SettingsEvent.FullScreenModePressed -> changeFullScreen()
            SettingsEvent.LandscapeRotatePressed -> changeRotation()
            SettingsEvent.PortraitRotatePressed -> changeRotation()
            SettingsEvent.RotateSwitchChecked -> changeAutorotation()
            SettingsEvent.AppSettingsPressed -> viewAction = SettingsAction.GoToAppSettings
            is SettingsEvent.SetButtonOpacity -> saveButtonOpacity(viewEvent.opacity)
            is SettingsEvent.SetButtonSize -> saveButtonSize(viewEvent.size)
        }
    }

    private fun saveButtonOpacity(opacity: Float) {
        viewState = viewState.copy(
            buttonOpacity = opacity
        )

        settingsInteractor.setFloatingButtonOpacity(opacity)
    }

    private fun saveButtonSize(size: ButtonSize) {
        viewState = viewState.copy(
            buttonSize = size
        )

        settingsInteractor.setFloatingButtonSize(size)
    }

    private fun changeAutorotation() {
        val autoRotateEnabled = !viewState.rotation.autoRotateEnabled

        viewState = viewState.copy(
            rotation = viewState.rotation.copy(
                autoRotateEnabled = autoRotateEnabled
            )
        )

        settingsInteractor.setAutorotate(autoRotateEnabled)
    }

    private fun changeRotation() {
        val rotation = if (viewState.rotation.rotateDirection == RotateDirection.Portrait) {
            RotateDirection.Landscape
        } else {
            RotateDirection.Portrait
        }

        viewState = viewState.copy(
            rotation = viewState.rotation.copy(
                rotateDirection = rotation
            )
        )

        settingsInteractor.setRotation(rotation)
    }

    private fun changeFullScreen() {
        val fullScreenMode = !viewState.fullScreenMode

        viewState = viewState.copy(
            fullScreenMode = fullScreenMode
        )

        settingsInteractor.setFullScreenMode(fullScreenMode)
    }
}