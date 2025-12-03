package com.posse.tanksrandomizer.feature_online_navigation.common.data.datasource

import com.posse.tanksrandomizer.feature_online_navigation.common.data.models.OnlineScreensData
import com.posse.tanksrandomizer.feature_online_navigation.common.data.models.toDataOnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.data.models.toOnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class PagedOnlineScreenDataSourceImpl(
    private val settings: Settings
) : PagedOnlineScreenDataSource {
    override fun getOnlineScreens(): OnlineScreens? {
        return getOnlineScreensData()
            ?.map { it.toOnlineScreenData() }
    }

    override fun setOnlineScreens(screens: OnlineScreens) {
        screens
            .map { it.toDataOnlineScreenData() }
            .let { setOnlineScreensData(it) }
    }

    override fun setOnlineScreen(screen: OnlineScreenData) {
        getOnlineScreensData()
            ?.map {
                if (it.id == screen.id) screen.toDataOnlineScreenData()
                else it
            }
            ?.let { setOnlineScreensData(it) }
    }

    private fun getOnlineScreensData(): OnlineScreensData? {
        return settings.decodeValueOrNull<OnlineScreensData>(ONLINE_SCREENS_KEY)
    }

    private fun setOnlineScreensData(screens: OnlineScreensData) {
        settings.encodeValue(ONLINE_SCREENS_KEY, screens)
    }

    companion object {
        private const val ONLINE_SCREENS_KEY = "OnlineScreens"
    }
}
