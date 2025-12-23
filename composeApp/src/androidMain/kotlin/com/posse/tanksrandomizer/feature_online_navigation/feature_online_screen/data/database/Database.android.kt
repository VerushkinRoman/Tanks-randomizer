package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration

actual fun getDatabaseBuilder(platformConfiguration: PlatformConfiguration): RoomDatabase.Builder<AppDatabase> {
    val appContext = platformConfiguration.androidContext.applicationContext
    val dbFile = appContext.getDatabasePath("tanks.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
