package com.posse.tanksrandomizer.android_mode.presentation

import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeAction
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeEvent
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeState
import com.posse.tanksrandomizer.android_mode.presentation.use_cases.GetAndroidModeState
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AndroidModeViewModel(
    val settingsInteractor: SettingsInteractor = Inject.instance(),
) : BaseSharedViewModel<AndroidModeState, AndroidModeAction, AndroidModeEvent>(
    initialState = GetAndroidModeState(settingsInteractor = settingsInteractor).invoke()
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
        }
    }

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

    public override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancelChildren(CancellationException("onCleared"))
    }
}
