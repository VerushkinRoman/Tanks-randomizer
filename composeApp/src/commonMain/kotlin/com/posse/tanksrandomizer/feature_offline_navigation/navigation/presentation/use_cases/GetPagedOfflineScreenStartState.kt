package com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.interactor.ScreensInteractor
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.ScreenMetadata
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.GetPagedScreenStartState
import com.posse.tanksrandomizer.feature_offline_navigation.common.domain.models.OfflineScreenData

class GetPagedOfflineScreenStartState(
    screensInteractor: ScreensInteractor,
) : GetPagedScreenStartState(
    screensInteractor = screensInteractor
) {
    override val emptyScreen: PagedScreen<Unit>
        get() = OfflineScreenData(
            metadata = ScreenMetadata(
                name = "1",
                position = 0,
                selected = true,
            )
        )
}
