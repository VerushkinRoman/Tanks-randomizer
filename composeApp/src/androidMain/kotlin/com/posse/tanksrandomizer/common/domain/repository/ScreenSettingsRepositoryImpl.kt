package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.ScreenSettingsDataSource
import com.posse.tanksrandomizer.common.domain.model.ButtonOffset

class ScreenSettingsRepositoryImpl(
    private val dataSource: ScreenSettingsDataSource
) : ScreenSettingsRepository {
    override fun getWindowInFullScreen(): Boolean = dataSource.getWindowInFullScreen()
    override fun setWindowInFullScreen(enabled: Boolean) = dataSource.setWindowInFullScreen(enabled)
    override fun getButtonOffset(): ButtonOffset? = dataSource.getButtonOffset()
    override fun setButtonOffset(buttonOffset: ButtonOffset) = dataSource.setButtonOffset(buttonOffset)
}