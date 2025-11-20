package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoAccounts
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.NoAccounts
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.navigation3.runtime.NavKey
import com.posse.tanksrandomizer.navigation.compose.components.NavigationItem.Offline
import com.posse.tanksrandomizer.navigation.compose.components.NavigationItem.Online
import com.posse.tanksrandomizer.navigation.compose.components.NavigationItem.Settings
import com.posse.tanksrandomizer.navigation.presentation.screens.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.OnlineNavigationRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.SettingsScreenRoute
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.lesta_logo
import tanks_randomizer.composeapp.generated.resources.lesta_logo_outline
import tanks_randomizer.composeapp.generated.resources.navigation_label_offline
import tanks_randomizer.composeapp.generated.resources.navigation_label_online
import tanks_randomizer.composeapp.generated.resources.navigation_label_settings

enum class NavigationItem(val label: StringResource, val screenRoute: NavKey) {
    Online(label = Res.string.navigation_label_online, screenRoute = OnlineNavigationRoute),
    Offline(label = Res.string.navigation_label_offline, screenRoute = OfflineScreenRoute),
    Settings(label = Res.string.navigation_label_settings, screenRoute = SettingsScreenRoute),
}

@Composable
fun NavigationIcon(
    item: NavigationItem,
    selected: Boolean,
) {
    Image(
        painter = when (item) {
            Online -> painterResource(
                if (selected) Res.drawable.lesta_logo else Res.drawable.lesta_logo_outline
            )

            Offline -> rememberVectorPainter(
                if (selected) Icons.Filled.NoAccounts else Icons.Outlined.NoAccounts
            )

            Settings -> rememberVectorPainter(
                if (selected) Icons.Filled.Settings else Icons.Outlined.Settings
            )
        },
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        colorFilter = ColorFilter.tint(LocalContentColor.current),
        modifier = Modifier.height(ButtonDefaults.MinHeight * 3 / 4)
    )
}
