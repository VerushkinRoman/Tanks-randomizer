package com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.utils.MAX_ONLINE_SCREENS
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.AddScreenResult
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.ScreenMetadata
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.AddNewScreen
import com.posse.tanksrandomizer.feature_offline_navigation.common.domain.models.OfflineScreenData

class AddNewOfflineScreen : AddNewScreen() {
    override operator fun invoke(screens: List<PagedScreen<*>>): AddScreenResult {
        @Suppress("UNCHECKED_CAST")
        if (screens.size >= MAX_ONLINE_SCREENS) return AddScreenResult(screens)

        return AddScreenResult(
            screens.map {
                val metadata = it.metadata.copy(selected = false)
                it.withMetadata(metadata)
            } + OfflineScreenData(
                metadata = ScreenMetadata(
                    name = getNameForNewScreen(screens),
                    position = screens.size,
                    selected = true,
                )
            )
        )
    }

    private fun getNameForNewScreen(screens: List<PagedScreen<*>>): String {
        val names = screens.map { it.metadata.name }
        return (1..10)
            .firstOrNull { it.toString() !in names }
            ?.toString()
            ?: "1"
    }
}
