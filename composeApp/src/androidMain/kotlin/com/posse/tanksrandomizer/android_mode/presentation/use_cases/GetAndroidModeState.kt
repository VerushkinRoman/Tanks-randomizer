package com.posse.tanksrandomizer.android_mode.presentation.use_cases

import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.android_mode.presentation.model.AndroidModeState

class GetAndroidModeState(
    private val settingsInteractor: SettingsInteractor,
) {
    operator fun invoke(): AndroidModeState {
        return AndroidModeState(
            fullScreenModeEnabled = settingsInteractor.fullScreenModeEnabled.value,
        )
    }
}
