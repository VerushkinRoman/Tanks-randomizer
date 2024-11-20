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

    private fun <T : ItemStatus<T>> getProperty(defaultItem: T): T {
        val random = settings.getBoolean("${defaultItem.name}_random", defaultItem.random)
        val selected = settings.getBoolean("${defaultItem.name}_selected", defaultItem.selected)
        return defaultItem.copy(selected = selected, random = random)
    }

    override fun getQuantity(): Int = settings.getInt("quantity", 1)
    override fun setQuantity(quantity: Int) = settings.putInt("quantity", quantity)


    override fun getAutorotate(): Boolean = settings.getBoolean("Autorotate", true)
    override fun setAutorotate(autoRotate: Boolean) = settings.putBoolean("Autorotate", autoRotate)

    override fun getRotation(): String? = settings.getStringOrNull("Rotation")
    override fun setRotation(rotation: String) = settings.putString("Rotation", rotation)

    override fun getFullScreen(): Boolean = settings.getBoolean("FullScreen", true)
    override fun setFullScreen(enabled: Boolean) = settings.putBoolean("FullScreen", enabled)
}