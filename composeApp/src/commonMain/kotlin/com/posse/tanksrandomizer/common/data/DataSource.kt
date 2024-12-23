package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.domain.model.FilterObjects.ItemStatus

interface DataSource {
    fun <T : ItemStatus<T>> setProperties(properties: List<T>)
    fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T>

    fun setQuantity(quantity: Int)
    fun getQuantity(): Int
}