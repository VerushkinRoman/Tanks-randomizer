package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.model.ButtonOffset

interface ScreenSettingsRepository {
    fun getWindowInFullScreen(): Boolean
    fun setWindowInFullScreen(enabled: Boolean)

    fun getButtonLandscapeOffset(): ButtonOffset?
    fun setButtonLandscapeOffset(buttonOffset: ButtonOffset)

    fun getButtonPortraitOffset(): ButtonOffset?
    fun setButtonPortraitOffset(buttonOffset: ButtonOffset)
}