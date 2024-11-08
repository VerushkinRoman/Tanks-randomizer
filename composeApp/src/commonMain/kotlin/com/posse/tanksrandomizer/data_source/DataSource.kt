package com.posse.tanksrandomizer.data_source

import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus

interface DataSource {
    suspend fun <T : ItemStatus<T>> setProperties(properties: List<T>)
    suspend fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T>

    suspend fun setQuantity(quantity: Int)
    suspend fun getQuantity(): Int

    suspend fun getAutorotate(): Boolean
    suspend fun setAutorotate(autoRotate: Boolean)

    suspend fun getRotation(): String?
    suspend fun setRotation(rotation: String)
}