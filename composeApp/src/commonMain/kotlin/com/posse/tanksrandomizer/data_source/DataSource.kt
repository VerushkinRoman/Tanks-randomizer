package com.posse.tanksrandomizer.data_source

import com.posse.tanksrandomizer.repository.model.ItemInfo

interface DataSource {
    fun <T : ItemInfo<T>> setProperties(properties: List<T>)
    fun <T : ItemInfo<T>> getProperties(defaultItems: List<T>): List<T>

    fun <T : ItemInfo<T>> setProperty(property: T)
    fun <T : ItemInfo<T>> getProperty(defaultItem: T): T

    fun setQuantity(quantity: Int)
    fun getQuantity(): Int
}