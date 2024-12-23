package com.posse.tanksrandomizer.navigation.presentation

import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.navigation.presentation.model.NavigationAction
import com.posse.tanksrandomizer.navigation.presentation.model.NavigationEvent
import com.posse.tanksrandomizer.navigation.presentation.model.NavigationState
import com.posse.tanksrandomizer.navigation.presentation.use_cases.GetNavigationState
import kotlinx.coroutines.launch

class NavigationViewModel(
    settingsInteractor: SettingsInteractor = Inject.instance(),
) : BaseSharedViewModel<NavigationState, NavigationAction, NavigationEvent>(
    initialState = GetNavigationState(settingsInteractor = settingsInteractor).invoke()
) {
    init {
        withViewModelScope {
            launch {
                settingsInteractor.fullScreenModeEnabled.collect { fullScreen ->
                    viewState = viewState.copy(
                        fullScreenModeEnabled = fullScreen,
                    )
                }
            }

            launch {
                settingsInteractor.windowInFullScreen.collect { fullScreen ->
                    viewState = viewState.copy(
                        windowInFullScreen = fullScreen
                    )
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: NavigationEvent) {
        when (viewEvent) {
            NavigationEvent.ClearAction -> viewAction = null
            NavigationEvent.OnClosePress -> exitApp()
        }
    }

    private fun exitApp() {
        viewAction = NavigationAction.ExitApp
    }
}