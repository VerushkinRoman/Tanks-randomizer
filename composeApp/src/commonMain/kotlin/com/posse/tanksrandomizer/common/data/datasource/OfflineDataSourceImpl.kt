package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.common.domain.models.Token
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class OfflineDataSourceImpl(
    private val settings: Settings,
    private val dataSourceFor: DataSourceFor,
) : OfflineDataSource {
    override fun <T : ItemStatus<T>> setProperties(properties: List<T>) {
        properties.forEach { setProperty(it) }
    }

    override fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T> {
        return defaultItems.map { getProperty(it) }
    }

    override fun getScreenRoute(): String? = settings.getStringOrNull(key = "last_screen")
    override fun setCurrentScreenRoute(screenRoute: String) {
        settings.putString(key = "last_screen", value = screenRoute)
    }

    override fun getToken(): Token? = settings.decodeValueOrNull(key = "token")
    override fun setToken(token: Token?) = settings.encodeValue(key = "token", value = token)

    override fun getNickname(): String? = settings.getStringOrNull(key = "nickname")
    override fun setNickname(nickname: String?) {
        nickname?.let {
            settings.putString(key = "nickname", value = nickname)
        } ?: settings.remove("nickname")
    }

    private fun <T : ItemStatus<T>> setProperty(property: T) {
        settings.putBoolean(
            key = "${property.name}_random${dataSourceFor.value}",
            value = property.random,
        )

        settings.putBoolean(
            key = "${property.name}_selected${dataSourceFor.value}",
            value = property.selected,
        )
    }

    private fun <T : ItemStatus<T>> getProperty(defaultItem: T): T {
        val random = settings.getBoolean(
            key = "${defaultItem.name}_random${dataSourceFor.value}",
            defaultValue = defaultItem.random,
        )

        val selected = settings.getBoolean(
            key = "${defaultItem.name}_selected${dataSourceFor.value}",
            defaultValue = defaultItem.selected,
        )

        return defaultItem.copy(selected = selected, random = random)
    }
}
