package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.dao.EncyclopediaTanksDao
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.dao.MasteryTanksDao
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models.DBEncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models.DBMasteryTank

@Database(
    entities = [DBMasteryTank::class, DBEncyclopediaTank::class],
    version = 1
)

@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMasteryTanksDao(): MasteryTanksDao
    abstract fun getEncyclopediaTanksDao(): EncyclopediaTanksDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
    dispatchers: Dispatchers,
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(dispatchers.io)
        .build()
}

expect fun getDatabaseBuilder(platformConfiguration: PlatformConfiguration): RoomDatabase.Builder<AppDatabase>
