package com.posse.tanksrandomizer.feature_settings_screen.presentation

import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.common.compose.utils.RotateDirection
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsAction
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsState
import com.posse.tanksrandomizer.feature_settings_screen.presentation.use_cases.GetSettingsState
import com.posse.tanksrandomizer.feature_settings_screen.presentation.use_cases.SaveSettingsState
import kotlinx.coroutines.launch

class SettingsViewModel(
    repository: SettingsRepository = Inject.instance(),
    dispatchers: Dispatchers = Inject.instance(),
) : BaseSharedViewModel<SettingsState, SettingsAction, SettingsEvent>(
    initialState = GetSettingsState(repository).invoke()
) {
    private val saveSettingsState = SaveSettingsState(
        repository = repository,
        dispatchers = dispatchers,
    )

    override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            SettingsEvent.ClearAction -> viewAction = null
            SettingsEvent.BackPressed -> viewAction = SettingsAction.NavigateBack
            SettingsEvent.FullScreenModePressed -> changeFullScreen()
            SettingsEvent.LandscapeRotatePressed -> changeRotationToLandscape()
            SettingsEvent.PortraitRotatePressed -> changeRotationPortrait()
            SettingsEvent.RotateSwitchChecked -> changeAutorotation()
            SettingsEvent.AppSettingsPressed -> viewAction = SettingsAction.GoToAppSettings
        }
    }

    private fun changeAutorotation() {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                rotation = viewState.rotation.copy(
                    autoRotateEnabled = !viewState.rotation.autoRotateEnabled
                )
            )
        }
    }

    private fun changeRotationToLandscape() {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                rotation = viewState.rotation.copy(
                    rotateDirection = RotateDirection.Landscape,
                )
            )
        }
    }

    private fun changeRotationPortrait() {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                rotation = viewState.rotation.copy(
                    rotateDirection = RotateDirection.Portrait
                )
            )
        }
    }

    private fun changeFullScreen() {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                fullScreenMode = !viewState.fullScreenMode
            )
        }
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend () -> Unit
    ) {
        viewModelScope.launch {
            action()
            saveSettingsState(viewState)
        }
    }
}