package com.posse.tanksrandomizer.data_source

import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus
import com.russhwolf.settings.Settings
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.Volatile

class DataSourceMultiplatformSettings private constructor(
    private val settings: Settings = Settings()
): DataSource {
    override suspend fun <T : ItemStatus<T>> setProperties(properties: List<T>) {
        properties.forEach { property ->
            settings.putBoolean("${property.name}_random", property.random)
            settings.putBoolean("${property.name}_selected", property.selected)
        }
    }

    override suspend fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T> {
        return defaultItems.map { getProperty(it) }
    }

    @Suppress("RedundantSuspendModifier")
    private suspend fun <T : ItemStatus<T>> getProperty(defaultItem: T): T {
        val random = settings.getBoolean("${defaultItem.name}_random", defaultItem.random)
        val selected = settings.getBoolean("${defaultItem.name}_selected", defaultItem.selected)
        return defaultItem.copy(selected = selected, random = random)
    }

    override suspend fun getQuantity(): Int = settings.getInt("quantity", 1)
    override suspend fun setQuantity(quantity: Int) = settings.putInt("quantity", quantity)


    override suspend fun getAutorotate(): Boolean = settings.getBoolean("Autorotate", true)
    override suspend fun setAutorotate(autoRotate: Boolean) = settings.putBoolean("Autorotate", autoRotate)

    override suspend fun getRotation(): String? = settings.getStringOrNull("Rotation")
    override suspend fun setRotation(rotation: String) = settings.putString("Rotation", rotation)

    @OptIn(InternalCoroutinesApi::class)
    companion object : SynchronizedObject() {
        @Volatile
        private var instance: DataSource? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: DataSourceMultiplatformSettings().also { instance = it }
        }
    }
}