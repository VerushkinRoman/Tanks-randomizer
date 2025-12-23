package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import java.io.File

actual fun getDatabaseBuilder(platformConfiguration: PlatformConfiguration): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "tanks.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}
