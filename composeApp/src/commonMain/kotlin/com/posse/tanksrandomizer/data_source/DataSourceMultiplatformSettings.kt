package com.posse.tanksrandomizer.data_source

import com.posse.tanksrandomizer.repository.model.ItemInfo
import com.russhwolf.settings.Settings

class DataSourceMultiplatformSettings : DataSource {
    private val settings: Settings = Settings()

    override fun <T : ItemInfo<T>> setProperties(properties: List<T>) {
        properties.forEach { property ->
            val name = property::class.simpleName ?: return
            settings.putBoolean("$name${property.sort}_random", property.random)
            settings.putBoolean("$name${property.sort}_selected", property.selected)
        }
    }

    override fun <T : ItemInfo<T>> getProperties(defaultItems: List<T>): List<T> {
        return defaultItems.map { item ->
            getProperty(item)
        }
    }

    override fun <T : ItemInfo<T>> setProperty(property: T) {
        val name = property::class.simpleName ?: return
        settings.putBoolean("$name${property.sort}_random", property.random)
        settings.putBoolean("$name${property.sort}_selected", property.selected)
    }

    override fun <T : ItemInfo<T>> getProperty(defaultItem: T): T {
        val name = defaultItem::class.simpleName ?: return defaultItem
        val random = settings.getBoolean("$name${defaultItem.sort}_random", defaultItem.random)
        val selected =
            settings.getBoolean("$name${defaultItem.sort}_selected", defaultItem.selected)
        return defaultItem.copy(selected = selected, random = random)
    }

    override fun setQuantity(quantity: Int) {
        settings.putInt("quantity", quantity)
    }

    override fun getQuantity(): Int {
        return settings.getInt("quantity", 1)
    }
}