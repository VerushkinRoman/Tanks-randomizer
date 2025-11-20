package com.posse.tanksrandomizer.navigation.presentation.screens

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
object OnlineNavigationRoute : NavKey

@Serializable
object OfflineScreenRoute : NavKey

@Serializable
object SettingsScreenRoute : NavKey

fun NavKey.toRoute(): String {
    return toString()
        .substringAfterLast('.')
        .takeWhile { it != '@' }
}

val mainNavigationConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(OnlineNavigationRoute::class, OnlineNavigationRoute.serializer())
            subclass(OfflineScreenRoute::class, OfflineScreenRoute.serializer())
            subclass(SettingsScreenRoute::class, SettingsScreenRoute.serializer())
        }
    }
}
