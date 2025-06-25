package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.DataSourceType
import com.russhwolf.settings.Settings

class DataSourceMultiplatformSettings(
    private val settings: Settings,
    private val dataSourceType: DataSourceType,
) : DataSource {
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