package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.interactor.ScreensInteractor
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreensState

abstract class GetPagedScreenStartState(
    private val screensInteractor: ScreensInteractor,
) {
    operator fun invoke(): PagedScreensState {
        val screens = screensInteractor
            .screens
            .value
            .ifEmpty {
                listOf(emptyScreen)
                    .also { screensInteractor.setScreens(it) }
            }

        return PagedScreensState(
            screens = screens,
        )
    }

    internal abstract val emptyScreen: PagedScreen<*>
}
