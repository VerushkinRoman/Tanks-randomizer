package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.domain.model.FilterObjects.ItemStatus
import com.russhwolf.settings.Settings

class DataSourceMultiplatformSettings(
    private val settings: Settings = Settings()
) : DataSource {
    override fun <T : ItemStatus<T>> setProperties(properties: List<T>) {
        properties.forEach { property ->
            settings.putBoolean("${property.name}_random", property.random)
            settings.putBoolean("${property.name}_selected", property.selected)
        }
    }

    override fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T> {
        return defaultItems.map { getProperty(it) }
    }

    override fun getQuantity(): Int = settings.getInt("quantity", 1)
    override fun setQuantity(quantity: Int) = settings.putInt("quantity", quantity)

    private fun <T : ItemStatus<T>> getProperty(defaultItem: T): T {
        val random = settings.getBoolean("${defaultItem.name}_random", defaultItem.random)
        val selected = settings.getBoolean("${defaultItem.name}_selected", defaultItem.selected)
        return defaultItem.copy(selected = selected, random = random)
    }
}