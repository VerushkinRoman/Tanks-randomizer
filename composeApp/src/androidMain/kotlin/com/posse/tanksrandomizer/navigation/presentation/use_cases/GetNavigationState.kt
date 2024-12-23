package com.posse.tanksrandomizer.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.navigation.presentation.model.NavigationState

class GetNavigationState(
    private val settingsInteractor: SettingsInteractor,
) {
    operator fun invoke(): NavigationState {
        return NavigationState(
            fullScreenModeEnabled = settingsInteractor.fullScreenModeEnabled.value,
            windowInFullScreen = settingsInteractor.windowInFullScreen.value,
        )
    }
}