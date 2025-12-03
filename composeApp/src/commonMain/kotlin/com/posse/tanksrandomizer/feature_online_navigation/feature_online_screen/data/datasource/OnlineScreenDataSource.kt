package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.datasource

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MasteryTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank
import kotlinx.coroutines.flow.Flow

interface OnlineScreenDataSource {
    fun getLastAccountUpdated(accountId: Int): Flow<Long?>
    fun setLastAccountUpdated(accountId: Int, dateTime: Long?)

    fun getMasteryTanks(accountId: Int): Flow<List<MasteryTank>>
    suspend fun setMasteryTanks(tanks: List<MasteryTank>)
    suspend fun deleteMasteryTanks(accountId: Int)
    fun getAllMasteryTanks(): Flow<List<MasteryTank>>

    fun getSelectedTank(screenId: String): Tank?
    fun setSelectedTank(screenId: String, tank: Tank?)

    fun getEncyclopediaTanks(): Flow<List<EncyclopediaTank>>
    suspend fun setEncyclopediaTanks(tanks: List<EncyclopediaTank>)

    fun getLastEncyclopediaAllTanksUpdated(): Long?
    fun setLastEncyclopediaAllTanksUpdated(dateTime: Long)
}
