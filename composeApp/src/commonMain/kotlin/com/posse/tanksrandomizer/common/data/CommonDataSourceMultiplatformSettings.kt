package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.posse.tanksrandomizer.common.domain.models.ScreenRoute
import com.posse.tanksrandomizer.common.domain.models.Token
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class CommonDataSourceMultiplatformSettings(
    val settings: Settings,
    private val dataSourceType: DataSourceType,
) : OfflineCommonDataSource {
    override fun <T : ItemStatus<T>> setProperties(properties: List<T>) {
        properties.forEach { property ->
            settings.putBoolean(
                "${property.name}_random${dataSourceType.value}",
                property.random
            )

            settings.putBoolean(
                "${property.name}_selected${dataSourceType.value}",
                property.selected
            )
        }
    }

    override fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T> {
        return defaultItems.map { getProperty(it) }
    }

    override fun getScreenRoute(): ScreenRoute? = settings.decodeValueOrNull(key = "last_screen")
    override fun setCurrentScreenRoute(screenRoute: ScreenRoute) {
        settings.encodeValue(key = "last_screen", value = screenRoute)
    }

    override fun getToken(): Token? = settings.decodeValueOrNull(key = "Token")
    override fun setToken(token: Token?) = settings.encodeValue(key = "Token", value = token)

    private fun <T : ItemStatus<T>> getProperty(defaultItem: T): T {
        val random = settings.getBoolean(
            "${defaultItem.name}_random${dataSourceType.value}",
            defaultItem.random
        )

        val selected = settings.getBoolean(
            "${defaultItem.name}_selected${dataSourceType.value}",
            defaultItem.selected
        )

        return defaultItem.copy(selected = selected, random = random)
    }
}