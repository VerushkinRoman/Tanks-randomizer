package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import kotlinx.coroutines.flow.Flow

class ADRepositoryImpl(
    private val dataSource: OfflineDataSource
) : ADRepository {
    override fun getLastAdWatchTime(): Long? = dataSource.getLastAdWatchTime()
    override fun setLastAdWatchTime(time: Long) = dataSource.setLastAdWatchTime(time)

    override fun getAdCount(): Flow<Int> = dataSource.getAdCount()
    override fun setAdCount(count: Int) = dataSource.setAdCount(count)
}
