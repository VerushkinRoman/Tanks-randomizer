package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MAX_ONLINE_SCREENS
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.AddScreenResult

class AddNewScreen {
    operator fun invoke(screens: OnlineScreens): AddScreenResult {
        if (screens.size >= MAX_ONLINE_SCREENS) return AddScreenResult(screens)

        screens.find { it.accountId == null }?.let { emptyScreen ->
            val newScreens = screens.map { onlineScreenData ->
                if (onlineScreenData == emptyScreen) onlineScreenData.copy(selected = true)
                else onlineScreenData.copy(selected = false)
            }

            return AddScreenResult(screens = newScreens, emptyScreenPosition = emptyScreen.position)
        }

        return AddScreenResult(
            screens.map {
                it.copy(selected = false)
            } + OnlineScreenData(
                name = (screens.size + 1).toString(),
                position = screens.size,
                selected = true,
            )
        )
    }
}
