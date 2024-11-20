package com.posse.tanksrandomizer.common.data.di

import com.posse.tanksrandomizer.common.data.DataSource
import com.posse.tanksrandomizer.common.data.DataSourceMultiplatformSettings
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val dataSourceModule = DI.Module("DataSourceModule") {
    bind<DataSource>() with singleton {
        DataSourceMultiplatformSettings()
    }
}