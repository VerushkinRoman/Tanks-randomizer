package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.OnlineScreensInteractor
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensState

class GetPagedOnlineScreenStartState(
    private val onlineScreensInteractor: OnlineScreensInteractor,
) {
    operator fun invoke(): PagedOnlineScreensState {
        val screens = onlineScreensInteractor
            .onlineScreens
            .value
            .ifEmpty {
                listOf(
                    OnlineScreenData(
                        name = "1",
                        position = 0,
                        selected = true,
                    )
                )
                    .also {
                        onlineScreensInteractor.setOnlineScreens(it)
                    }
            }

        return PagedOnlineScreensState(
            screens = screens,
        )
    }
}
