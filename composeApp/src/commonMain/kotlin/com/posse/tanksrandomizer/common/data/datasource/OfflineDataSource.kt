package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import kotlinx.coroutines.flow.Flow

interface OfflineDataSource {
    fun <T : ItemStatus<T>> setProperties(id: String, properties: List<T>)
    fun <T : ItemStatus<T>> getProperties(id: String, defaultItems: List<T>): List<T>

    fun setCurrentScreenRoute(screenRoute: String)
    fun getScreenRoute(): String?

    fun getLastAdWatchTime(): Long?
    fun setLastAdWatchTime(time: Long)

    fun getAdCount(): Flow<Int>
    fun setAdCount(count: Int)
}
