package com.posse.tanksrandomizer.navigation.compose.components

import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OnlineNavigationRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.SettingsScreenRoute
import org.jetbrains.compose.resources.StringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.navigation_label_offline
import tanks_randomizer.composeapp.generated.resources.navigation_label_online
import tanks_randomizer.composeapp.generated.resources.navigation_label_settings

enum class NavigationItem(val label: StringResource, val screenRoute: ScreenRoute) {
    Online(label = Res.string.navigation_label_online, screenRoute = OnlineNavigationRoute),
    Offline(label = Res.string.navigation_label_offline, screenRoute = OfflineScreenRoute),
    Settings(label = Res.string.navigation_label_settings, screenRoute = SettingsScreenRoute),
}
