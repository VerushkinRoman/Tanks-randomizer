package com.posse.tanksrandomizer.common.data.di

import com.russhwolf.settings.Settings
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val dataSourceModule = DI.Module("DataSourceModule") {
    bind<Settings>() with singleton {
        Settings()
    }
}
