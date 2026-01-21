package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.common.domain.models.Token
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getIntFlow
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class OfflineDataSourceImpl(
    private val settings: Settings,
    private val observableSettings: ObservableSettings,
    private val dataSourceFor: DataSourceFor,
) : OfflineDataSource {
    override fun <T : ItemStatus<T>> setProperties(id: String, properties: List<T>) {
        properties.forEach { setProperty(id, it) }
    }

    override fun <T : ItemStatus<T>> getProperties(id: String, defaultItems: List<T>): List<T> {
        return defaultItems.map { getProperty(id, it) }
    }

    override fun getScreenRoute(): String? = settings.getStringOrNull(LAST_SCREEN_KEY)
    override fun setCurrentScreenRoute(screenRoute: String) = settings.putString(LAST_SCREEN_KEY, value = screenRoute)

    override fun getToken(accountId: Int): Token? = settings.decodeValueOrNull("${TOKEN_KEY}_$accountId")
    override fun setToken(accountId: Int, token: Token?) = settings.encodeValue("${TOKEN_KEY}_$accountId", value = token)

    override fun getLastAdWatchTime(): Long? = settings.getLongOrNull(LAST_AD_WATCH_KEY)
    override fun setLastAdWatchTime(time: Long) = settings.putLong(LAST_AD_WATCH_KEY, value = time)

    override fun getAdCount(): Flow<Int> = observableSettings.getIntFlow(AD_COUNT_KEY, 0)
    override fun setAdCount(count: Int) = observableSettings.putInt(AD_COUNT_KEY, value = count)

    private fun <T : ItemStatus<T>> setProperty(id: String, property: T) {
        settings.putBoolean(
            key = "${property.name}_${RANDOM_KEY}${dataSourceFor.value}_$id",
            value = property.random,
        )

        settings.putBoolean(
            key = "${property.name}_${SELECTED_KEY}${dataSourceFor.value}_$id",
            value = property.selected,
        )
    }

    private fun <T : ItemStatus<T>> getProperty(id: String, defaultItem: T): T {
        val random = settings.getBoolean(
            key = "${defaultItem.name}_${RANDOM_KEY}${dataSourceFor.value}_$id",
            defaultValue = defaultItem.random,
        )

        val selected = settings.getBoolean(
            key = "${defaultItem.name}_${SELECTED_KEY}${dataSourceFor.value}_$id",
            defaultValue = defaultItem.selected,
        )

        return defaultItem.copy(selected = selected, random = random)
    }

    companion object {
        private const val LAST_SCREEN_KEY = "last_screen"
        private const val TOKEN_KEY = "token"
        private const val RANDOM_KEY = "random"
        private const val SELECTED_KEY = "selected"
        private const val LAST_AD_WATCH_KEY = "last_ad_watch"
        private const val AD_COUNT_KEY = "ad_count_watch"
    }
}
