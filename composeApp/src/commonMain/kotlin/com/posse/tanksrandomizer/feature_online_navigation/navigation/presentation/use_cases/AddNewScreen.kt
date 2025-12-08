package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MAX_ONLINE_SCREENS
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens

class AddNewScreen {
    operator fun invoke(screens: OnlineScreens): OnlineScreens {
        return when {
            screens.size >= MAX_ONLINE_SCREENS -> screens

            screens.maxByOrNull { it.position }?.accountId == null -> {
                screens.map {
                    it.copy(selected = it.position == screens.size - 1)
                }
            }

            else -> {
                screens.map {
                    it.copy(selected = false)
                } + OnlineScreenData(
                    name = (screens.size + 1).toString(),
                    position = screens.size,
                    selected = true,
                )
            }
        }
    }
}
