package com.posse.tanksrandomizer.data_source

import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus

interface DataSource {
    fun <T : ItemStatus<T>> setProperties(properties: List<T>)
    fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T>

    fun setQuantity(quantity: Int)
    fun getQuantity(): Int
}