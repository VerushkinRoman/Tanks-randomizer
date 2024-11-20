package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.compose.utils.RotateDirection
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.data.DataSource

class SettingsRepositoryImpl(
    private val dataSource: DataSource = Inject.instance()
) : SettingsRepository {
    override fun getAutorotate(): Boolean = dataSource.getAutorotate()
    override fun setAutorotate(autoRotate: Boolean) = dataSource.setAutorotate(autoRotate)

    override fun getRotation(): RotateDirection {
        return RotateDirection.entries.find { it.value == dataSource.getRotation() }
            ?: RotateDirection.default
    }
    override fun setRotation(rotateDirection: RotateDirection) = dataSource.setRotation(rotateDirection.value)

    override fun getFullScreenMode(): Boolean = dataSource.getFullScreen()
    override fun setFullScreenMode(fullScreen: Boolean) = dataSource.setFullScreen(fullScreen)
}