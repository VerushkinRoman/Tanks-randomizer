package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models.DBMasteryTank
import kotlinx.coroutines.flow.Flow

@Dao
interface MasteryTanksDao{
    @Query("SELECT * FROM DBMasteryTank WHERE accountId = :accountId")
    fun getAllByAccountIdAsFlow(accountId: Int): Flow<List<DBMasteryTank>>

    @Query("SELECT * FROM DBMasteryTank")
    fun getAllAsFlow(): Flow<List<DBMasteryTank>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(masteryTanks: List<DBMasteryTank>)

    @Query("DELETE FROM DBMasteryTank WHERE accountId = :accountId")
    suspend fun deleteByAccountId(accountId: Int)
}
