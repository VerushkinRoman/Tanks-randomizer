package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.screens

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable data class Page0Navigation(val id: String) : NavKey
@Serializable data class Page1Navigation(val id: String) : NavKey
@Serializable data class Page2Navigation(val id: String) : NavKey
@Serializable data class Page3Navigation(val id: String) : NavKey
@Serializable data class Page4Navigation(val id: String) : NavKey
@Serializable data class Page5Navigation(val id: String) : NavKey
@Serializable data class Page6Navigation(val id: String) : NavKey
@Serializable data class Page7Navigation(val id: String) : NavKey
@Serializable data class Page8Navigation(val id: String) : NavKey
@Serializable data class Page9Navigation(val id: String) : NavKey

val pagedNavigationConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Page0Navigation::class, Page0Navigation.serializer())
            subclass(Page1Navigation::class, Page1Navigation.serializer())
            subclass(Page2Navigation::class, Page2Navigation.serializer())
            subclass(Page3Navigation::class, Page3Navigation.serializer())
            subclass(Page4Navigation::class, Page4Navigation.serializer())
            subclass(Page5Navigation::class, Page5Navigation.serializer())
            subclass(Page6Navigation::class, Page6Navigation.serializer())
            subclass(Page7Navigation::class, Page7Navigation.serializer())
            subclass(Page8Navigation::class, Page8Navigation.serializer())
            subclass(Page9Navigation::class, Page9Navigation.serializer())
        }
    }
}
