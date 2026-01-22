package com.posse.tanksrandomizer.feature_offline_navigation.common.data.datasource

import com.posse.tanksrandomizer.common.paged_screens_navigation.data.datasource.PagedScreenDataSource
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.feature_offline_navigation.common.data.models.DataOfflineScreenData
import com.posse.tanksrandomizer.feature_offline_navigation.common.data.models.toDataOfflineScreenData
import com.posse.tanksrandomizer.feature_offline_navigation.common.data.models.toOfflineScreenData
import com.posse.tanksrandomizer.feature_offline_navigation.common.domain.models.OfflineScreenData
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class PagedOfflineScreenDataSourceImpl(
    private val settings: Settings
) : PagedScreenDataSource {
    override fun getScreens(): List<OfflineScreenData>? {
        return getOfflineScreensData()
            ?.map { it.toOfflineScreenData() }
    }

    override fun setScreens(screens: List<PagedScreen<*>>) {
        screens
            .mapNotNull {
                if (it !is OfflineScreenData) return@mapNotNull null
                it.toDataOfflineScreenData()
            }
            .let { setOfflineScreensData(it) }
    }

    override fun setScreen(screen: PagedScreen<*>) {
        getOfflineScreensData()
            ?.map {
                if (screen is OfflineScreenData && it.id == screen.metadata.id) screen.toDataOfflineScreenData()
                else it
            }
            ?.let { setOfflineScreensData(it) }
    }

    override fun getAccounts(): Set<Int>? = null

    private fun getOfflineScreensData(): List<DataOfflineScreenData>? {
        return settings.decodeValueOrNull<List<DataOfflineScreenData>>(OFFLINE_SCREENS_KEY)
    }

    private fun setOfflineScreensData(screens: List<DataOfflineScreenData>) {
        settings.encodeValue(OFFLINE_SCREENS_KEY, screens)
    }

    companion object {
        private const val OFFLINE_SCREENS_KEY = "OfflineScreens"
    }
}
