package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.model.ButtonOffset

interface ScreenSettingsRepository {
    fun getWindowInFullScreen(): Boolean
    fun setWindowInFullScreen(enabled: Boolean)

    fun getButtonOffset(): ButtonOffset?
    fun setButtonOffset(buttonOffset: ButtonOffset)
}