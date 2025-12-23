package com.posse.tanksrandomizer.feature_offline_navigation.common.presentation.interactor

import com.posse.tanksrandomizer.common.paged_screens_navigation.domain.repository.PagedScreenRepository
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.interactor.ScreensInteractor
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OfflineScreensInteractorImpl(
    private val pagedScreenRepository: PagedScreenRepository
) : ScreensInteractor {
    private val _offlineScreens = MutableStateFlow(pagedScreenRepository.getScreens() ?: emptyList())
    override val screens: StateFlow<List<PagedScreen<*>>> = _offlineScreens.asStateFlow()

    override fun setScreens(screens: List<PagedScreen<*>>) {
        _offlineScreens.value = screens
        pagedScreenRepository.setScreens(screens)
    }
}
