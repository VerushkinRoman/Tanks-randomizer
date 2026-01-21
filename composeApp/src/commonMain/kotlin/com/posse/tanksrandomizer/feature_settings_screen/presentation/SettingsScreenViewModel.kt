package com.posse.tanksrandomizer.feature_settings_screen.presentation

import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsState
import com.posse.tanksrandomizer.feature_settings_screen.presentation.use_cases.GetSettingsState
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val settingsInteractor: SettingsInteractor,
) : BaseSharedViewModel<SettingsState, SettingsAction, SettingsEvent>(
    initialState = GetSettingsState(settingsInteractor.settingsRepository).invoke()
) {
    init {
        withViewModelScope {
            launch {
                settingsInteractor.screenRotation.collect { rotation ->
                    viewState = viewState.copy(
                        screenRotation = rotation
                    )
                }
            }

            launch {
                settingsInteractor.settingsRepository.getMultiaccountEnabled().collect {
                    viewState = viewState.copy(
                        multiaccountEnabled = it
                    )
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            SettingsEvent.ClearAction -> viewAction = null
            SettingsEvent.OverlayButtonPressed -> viewAction = SettingsAction.GoToAppSettings
            is SettingsEvent.RotationChanged -> changeRotation(viewEvent.screenRotation)
            is SettingsEvent.SetButtonOpacity -> saveButtonOpacity(viewEvent.opacity)
            is SettingsEvent.SetButtonSize -> saveButtonSize(viewEvent.size)
            is SettingsEvent.FullScreenModeChanged -> changeFullScreen(viewEvent.fullScreen)
            is SettingsEvent.ChangeLocale -> changeLocale(viewEvent.locale)
            is SettingsEvent.MultiaccountEnabled -> changeMultiaccount(viewEvent.enabled)
            is SettingsEvent.AutoHideChanged -> changeAutoHide(viewEvent.enabled)
        }
    }

    private fun changeLocale(locale: AppLocale) {
        viewState = viewState.copy(
            locale = locale
        )

        settingsInteractor.settingsRepository.setLocale(locale)

        viewAction = SettingsAction.UpdateLocale(locale)
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
        settingsInteractor.setScreenRotation(screenRotation)
    }

    private fun changeFullScreen(fullScreen: Boolean) {
        if (viewState.fullScreenMode == fullScreen) return

        viewState = viewState.copy(
            fullScreenMode = fullScreen
        )

        settingsInteractor.setFullScreenMode(fullScreen)

        viewAction = SettingsAction.FullScreenChanged(fullScreen)
    }

    private fun changeMultiaccount(enabled: Boolean) {
        settingsInteractor.settingsRepository.setMultiaccountEnabled(enabled)
    }

    private fun changeAutoHide(enabled: Boolean) {
        settingsInteractor.settingsRepository.setAutohideEnabled(enabled)
        viewState = viewState.copy(autoHide = enabled)
    }
}
