package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens

class ChangeName {
    operator fun invoke(id: String, name: String, screens: OnlineScreens): OnlineScreens {
        return screens
            .map { data ->
                if (data.id == id) data.copy(name = name)
                else data
            }
    }
}
