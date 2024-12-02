package com.posse.tanksrandomizer.common.data.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.data.ScreenSettingsDataSource
import com.posse.tanksrandomizer.common.data.ScreenSettingsDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val screenSettingsDataSourceModule = DI.Module("ScreenSettingsDataSourceModule") {
    bind<ScreenSettingsDataSource>() with singleton {
        val preferences: SharedPreferences = instance<PlatformConfiguration>()
            .androidContext
            .getSharedPreferences(
                /* name = */ "ScreenConfiguration",
                /* mode = */ MODE_PRIVATE
            )
        ScreenSettingsDataSourceImpl(preferences)
    }
}