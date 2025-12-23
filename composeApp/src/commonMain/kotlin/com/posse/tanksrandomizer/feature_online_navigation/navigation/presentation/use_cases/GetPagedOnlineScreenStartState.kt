package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.ScreenMetadata
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.GetPagedScreenStartState
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.OnlineScreensInteractor

class GetPagedOnlineScreenStartState(
    onlineScreensInteractor: OnlineScreensInteractor,
) : GetPagedScreenStartState(
    screensInteractor = onlineScreensInteractor
) {
    override val emptyScreen: PagedScreen<Int?>
        get() = OnlineScreenData(
            metadata = ScreenMetadata(
                name = "1",
                position = 0,
                selected = true,
            ),
            additionalData = null
        )
}
