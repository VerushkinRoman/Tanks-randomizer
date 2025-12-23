package com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.RemoveScreen

class RemoveOfflineScreen : RemoveScreen() {
    override operator fun invoke(
        id: String,
        screens: List<PagedScreen<*>>
    ): List<PagedScreen<*>> {
        val deletedScreen = screens.find { it.metadata.id == id } ?: return screens
        val remainingScreens = screens - deletedScreen

        val selectedIndex = maxOf(0, deletedScreen.metadata.position - 1)
        return remainingScreens.mapIndexed { index, screen ->
            val metadata = screen.metadata.copy(
                position = index,
                selected = if (deletedScreen.metadata.selected) index == selectedIndex else screen.metadata.selected
            )
            screen.withMetadata(metadata)
        }
    }
}
