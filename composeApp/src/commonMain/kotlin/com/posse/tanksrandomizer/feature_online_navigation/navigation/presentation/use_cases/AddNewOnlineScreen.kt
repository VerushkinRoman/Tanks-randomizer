package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.AddScreenResult
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.ScreenMetadata
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.AddNewScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.utils.MAX_ONLINE_SCREENS
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData

class AddNewOnlineScreen : AddNewScreen() {
    override operator fun invoke(screens: List<PagedScreen<*>>): AddScreenResult {
        val onlineScreensData = screens.filterIsInstance<OnlineScreenData>()

        if (onlineScreensData.size >= MAX_ONLINE_SCREENS) return AddScreenResult(onlineScreensData)

        onlineScreensData.find { it.accountId == null }?.let { emptyScreen ->
            val newScreens = onlineScreensData.map { onlineScreenData ->
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
            onlineScreensData.map {
                val metadata = it.metadata.copy(selected = false)
                it.withMetadata(metadata)
            } + OnlineScreenData(
                metadata = ScreenMetadata(
                    name = null,
                    position = onlineScreensData.size,
                    selected = true,
                ),
                additionalData = null
            )
        )
    }
}
