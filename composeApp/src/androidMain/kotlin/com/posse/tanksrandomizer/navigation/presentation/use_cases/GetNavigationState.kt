package com.posse.tanksrandomizer.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.presentation.interactor.FullScreenModeInteractor
import com.posse.tanksrandomizer.common.presentation.interactor.ScreenSettingsInteractor
import com.posse.tanksrandomizer.navigation.presentation.model.NavigationState

class GetNavigationState(
    private val fullScreenModeInteractor: FullScreenModeInteractor,
    private val screenSettingsInteractor: ScreenSettingsInteractor,
) {
    operator fun invoke(): NavigationState {
        return NavigationState(
            fullScreenModeEnabled = fullScreenModeInteractor.fullScreenModeEnabled.value,
            windowInFullScreen = screenSettingsInteractor.windowInFullScreen.value,
        )
    }
}