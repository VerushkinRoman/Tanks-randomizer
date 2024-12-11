package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.domain.model.ButtonOffset

interface ScreenSettingsDataSource {
    fun getWindowInFullScreen(): Boolean
    fun setWindowInFullScreen(enabled: Boolean)

    fun getButtonLandscapeOffset(): ButtonOffset?
    fun setButtonLandscapeOffset(buttonOffset: ButtonOffset)

    fun getButtonPortraitOffset(): ButtonOffset?
    fun setButtonPortraitOffset(buttonOffset: ButtonOffset)
}