package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models.DBEncyclopediaTank
import kotlinx.coroutines.flow.Flow

@Dao
interface EncyclopediaTanksDao {
    @Query("SELECT * FROM DBEncyclopediaTank")
    fun getAllAsFlow(): Flow<List<DBEncyclopediaTank>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceAll(tanks: List<DBEncyclopediaTank>)
}
