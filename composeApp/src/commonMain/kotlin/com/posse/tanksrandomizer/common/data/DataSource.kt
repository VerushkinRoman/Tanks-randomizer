package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus

interface DataSource {
    fun <T : ItemStatus<T>> setProperties(properties: List<T>)
    fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T>
}