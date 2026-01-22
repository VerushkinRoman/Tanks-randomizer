package com.posse.tanksrandomizer.common.paged_screens_navigation.data.datasource

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.data.models.DataOnlineScreenData
import com.posse.tanksrandomizer.common.paged_screens_navigation.data.models.toDataOnlineScreenData
import com.posse.tanksrandomizer.common.paged_screens_navigation.data.models.toOnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class PagedOnlineScreenDataSourceImpl(
    private val settings: Settings
) : PagedScreenDataSource {
    override fun getScreens(): List<OnlineScreenData>? {
        return getOnlineScreensData()
            ?.map { it.toOnlineScreenData() }
    }

    override fun setScreens(screens: List<PagedScreen<*>>) {
        screens
            .mapNotNull {
                if (it !is OnlineScreenData) return@mapNotNull null
                it.toDataOnlineScreenData()
            }
            .let { setOnlineScreensData(it) }
    }

    override fun setScreen(screen: PagedScreen<*>) {
        getOnlineScreensData()
            ?.map {
                if (screen is OnlineScreenData && it.id == screen.metadata.id) screen.toDataOnlineScreenData()
                else it
            }
            ?.let { setOnlineScreensData(it) }
    }

    override fun getAccounts(): Set<Int>? {
        return getOnlineScreensData()
            ?.mapNotNull { it.accountId }
            ?.toSet()
    }

    private fun getOnlineScreensData(): List<DataOnlineScreenData>? {
        return settings.decodeValueOrNull<List<DataOnlineScreenData>>(ONLINE_SCREENS_KEY)
    }

    private fun setOnlineScreensData(screens: List<DataOnlineScreenData>) {
        settings.encodeValue(ONLINE_SCREENS_KEY, screens)
    }

    companion object {
        private const val ONLINE_SCREENS_KEY = "OnlineScreens"
    }
}