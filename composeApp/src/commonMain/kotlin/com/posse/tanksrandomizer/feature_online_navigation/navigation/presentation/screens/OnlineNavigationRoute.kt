package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
object MainScreenRoute : NavKey

@Serializable
object OnlineScreenRoute : NavKey

@Serializable
data class WebViewScreenRoute(val url: String) : NavKey

val onlineNavigationConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(MainScreenRoute::class, MainScreenRoute.serializer())
            subclass(OnlineScreenRoute::class, OnlineScreenRoute.serializer())
            subclass(WebViewScreenRoute::class, WebViewScreenRoute.serializer())
        }
    }
}
