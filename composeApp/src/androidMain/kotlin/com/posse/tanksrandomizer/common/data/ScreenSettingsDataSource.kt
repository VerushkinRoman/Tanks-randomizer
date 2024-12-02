package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.domain.model.ButtonOffset

interface ScreenSettingsDataSource {
    fun getWindowInFullScreen(): Boolean
    fun setWindowInFullScreen(enabled: Boolean)

    fun getButtonOffset(): ButtonOffset?
    fun setButtonOffset(buttonOffset: ButtonOffset)
}