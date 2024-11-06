package com.posse.tanksrandomizer.data_source

import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus
import com.russhwolf.settings.Settings

class DataSourceMultiplatformSettings : DataSource {
    private val settings: Settings = Settings()

    override fun <T : ItemStatus<T>> setProperties(properties: List<T>) {
        properties.forEach { property ->
            settings.putBoolean("${property.name}_random", property.random)
            settings.putBoolean("${property.name}_selected", property.selected)
        }
    }

    override fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T> {
        return defaultItems.map { getProperty(it) }
    }

    private fun <T : ItemStatus<T>> getProperty(defaultItem: T): T {
        val random = settings.getBoolean("${defaultItem.name}_random", defaultItem.random)
        val selected = settings.getBoolean("${defaultItem.name}_selected", defaultItem.selected)
        return defaultItem.copy(selected = selected, random = random)
    }

    override fun setQuantity(quantity: Int) {
        settings.putInt("quantity", quantity)
    }

    override fun getQuantity(): Int {
        return settings.getInt("quantity", 1)
    }
}