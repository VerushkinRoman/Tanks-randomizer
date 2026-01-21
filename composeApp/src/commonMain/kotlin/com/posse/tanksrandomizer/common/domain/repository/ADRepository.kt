package com.posse.tanksrandomizer.common.domain.repository

import kotlinx.coroutines.flow.Flow

interface ADRepository {
    fun getLastAdWatchTime(): Long?
    fun setLastAdWatchTime(time: Long)

    fun getAdCount(): Flow<Int>
    fun setAdCount(count: Int)
}