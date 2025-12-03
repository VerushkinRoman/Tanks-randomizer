package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens

class SelectScreen {
    operator fun invoke(id: String, screens: OnlineScreens): OnlineScreens {
        return screens
            .map { data ->
                data.copy(
                    selected = data.id == id
                )
            }
    }
}
