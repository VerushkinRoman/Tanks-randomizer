package com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.PagedScreensViewModel
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.interactor.ScreensInteractor
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.AddNewScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.RemoveScreen
import com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.use_cases.AddNewOfflineScreen
import com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.use_cases.GetPagedOfflineScreenStartState
import com.posse.tanksrandomizer.feature_offline_navigation.navigation.presentation.use_cases.RemoveOfflineScreen

class PagedOfflineScreensViewModel(
    screensInteractor: ScreensInteractor,
) : PagedScreensViewModel(
    screensInteractor = screensInteractor,
    getPagedScreenStartState = GetPagedOfflineScreenStartState(
        screensInteractor = screensInteractor,
    ),
) {
    override val removeScreen: RemoveScreen = RemoveOfflineScreen()
    override val addNewScreen: AddNewScreen = AddNewOfflineScreen()
}
