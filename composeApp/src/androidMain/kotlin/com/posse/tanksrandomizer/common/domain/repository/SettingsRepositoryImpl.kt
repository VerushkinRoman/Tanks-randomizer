package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection
import com.posse.tanksrandomizer.common.data.ScreenSettingsDataSource
import com.posse.tanksrandomizer.common.domain.model.ButtonOffset
import com.posse.tanksrandomizer.common.domain.model.ButtonSize

class SettingsRepositoryImpl(
    private val dataSource: ScreenSettingsDataSource
) : SettingsRepository {
    override fun getAutorotate(): Boolean = dataSource.getAutorotate()
    override fun setAutorotate(autoRotate: Boolean) = dataSource.setAutorotate(autoRotate)

    override fun getRotation(): RotateDirection {
        return RotateDirection.entries.find { it.value == dataSource.getRotation() }
            ?: RotateDirection.default
    }

    override fun setRotation(rotateDirection: RotateDirection) = dataSource.setRotation(rotateDirection.value)

    override fun getFullScreenMode(): Boolean = dataSource.getFullScreenMode()
    override fun setFullScreenMode(fullScreen: Boolean) = dataSource.setFullScreenMode(fullScreen)

    override fun getWindowInFullScreen(): Boolean = dataSource.getWindowInFullScreen()
    override fun setWindowInFullScreen(enabled: Boolean) = dataSource.setWindowInFullScreen(enabled)

    override fun getButtonLandscapeOffset(): ButtonOffset? = dataSource.getButtonLandscapeOffset()
    override fun setButtonLandscapeOffset(buttonOffset: ButtonOffset) = dataSource.setButtonLandscapeOffset(buttonOffset)

    override fun getButtonPortraitOffset(): ButtonOffset? = dataSource.getButtonPortraitOffset()
    override fun setButtonPortraitOffset(buttonOffset: ButtonOffset) = dataSource.setButtonPortraitOffset(buttonOffset)

    override fun getFloatingButtonOpacity(): Float = dataSource.getFloatingButtonOpacity()
    override fun setFloatingButtonOpacity(opacity: Float) = dataSource.setFloatingButtonOpacity(opacity)

    override fun getFloatingButtonSize(): ButtonSize = dataSource.getFloatingButtonSize()
    override fun setFloatingButtonSize(size: ButtonSize) = dataSource.setFloatingButtonSize(size)
}