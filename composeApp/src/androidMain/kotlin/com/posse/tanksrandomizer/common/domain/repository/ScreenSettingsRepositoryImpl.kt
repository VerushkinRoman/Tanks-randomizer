package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.ScreenSettingsDataSource
import com.posse.tanksrandomizer.common.domain.model.ButtonOffset

class ScreenSettingsRepositoryImpl(
    private val dataSource: ScreenSettingsDataSource
) : ScreenSettingsRepository {
    override fun getWindowInFullScreen(): Boolean = dataSource.getWindowInFullScreen()
    override fun setWindowInFullScreen(enabled: Boolean) = dataSource.setWindowInFullScreen(enabled)

    override fun getButtonLandscapeOffset(): ButtonOffset? = dataSource.getButtonLandscapeOffset()
    override fun setButtonLandscapeOffset(buttonOffset: ButtonOffset) = dataSource.setButtonLandscapeOffset(buttonOffset)

    override fun getButtonPortraitOffset(): ButtonOffset? = dataSource.getButtonPortraitOffset()
    override fun setButtonPortraitOffset(buttonOffset: ButtonOffset) = dataSource.setButtonPortraitOffset(buttonOffset)
}