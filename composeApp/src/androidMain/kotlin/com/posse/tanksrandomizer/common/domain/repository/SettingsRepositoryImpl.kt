package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.compose.utils.ScreenRotation
import com.posse.tanksrandomizer.common.data.ScreenSettingsDataSource
import com.posse.tanksrandomizer.common.domain.models.ButtonOffset

class SettingsRepositoryImpl(
    private val dataSource: ScreenSettingsDataSource
) : SettingsRepository {
    override fun getRotation(): ScreenRotation = dataSource.getRotation() ?: ScreenRotation.Auto
    override fun setRotation(screenRotation: ScreenRotation) = dataSource.setRotation(screenRotation)

    override fun getFullScreenMode(): Boolean = dataSource.getFullScreenMode()
    override fun setFullScreenMode(fullScreen: Boolean) = dataSource.setFullScreenMode(fullScreen)

    override fun getButtonLandscapeOffset(): ButtonOffset? = dataSource.getButtonLandscapeOffset()
    override fun setButtonLandscapeOffset(buttonOffset: ButtonOffset) = dataSource.setButtonLandscapeOffset(buttonOffset)

    override fun getButtonPortraitOffset(): ButtonOffset? = dataSource.getButtonPortraitOffset()
    override fun setButtonPortraitOffset(buttonOffset: ButtonOffset) = dataSource.setButtonPortraitOffset(buttonOffset)

    override fun getFloatingButtonOpacity(): Float = dataSource.getFloatingButtonOpacity()
    override fun setFloatingButtonOpacity(opacity: Float) = dataSource.setFloatingButtonOpacity(opacity)

    override fun getFloatingButtonSize(): Float = dataSource.getFloatingButtonSize()
    override fun setFloatingButtonSize(size: Float) = dataSource.setFloatingButtonSize(size)
}
