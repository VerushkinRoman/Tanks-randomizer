package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.AddScreenResult
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.ScreenMetadata
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.AddNewScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.utils.MAX_ONLINE_SCREENS
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData

class AddNewOnlineScreen : AddNewScreen() {
    override operator fun invoke(screens: List<PagedScreen<*>>): AddScreenResult {
        @Suppress("UNCHECKED_CAST")
        screens as List<OnlineScreenData>

        if (screens.size >= MAX_ONLINE_SCREENS) return AddScreenResult(screens)

        screens.find { it.accountId == null }?.let { emptyScreen ->
            val newScreens = screens.map { onlineScreenData ->
                val metadata = onlineScreenData.metadata.copy(
                    selected = onlineScreenData == emptyScreen
                )

                onlineScreenData.withMetadata(metadata)
            }

            return AddScreenResult(
                screens = newScreens,
                emptyScreenPosition = emptyScreen.metadata.position
            )
        }

        return AddScreenResult(
            screens.map {
                val metadata = it.metadata.copy(selected = false)
                it.withMetadata(metadata)
            } + OnlineScreenData(
                metadata = ScreenMetadata(
                    name = null,
                    position = screens.size,
                    selected = true,
                ),
                additionalData = null
            )
        )
    }
}
