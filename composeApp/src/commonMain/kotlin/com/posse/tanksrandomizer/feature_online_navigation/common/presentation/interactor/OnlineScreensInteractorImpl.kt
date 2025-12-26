package com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor

import com.posse.tanksrandomizer.common.paged_screens_navigation.domain.repository.PagedScreenRepository
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnlineScreensInteractorImpl(
    private val pagedScreenRepository: PagedScreenRepository,
) : OnlineScreensInteractor {
    private val _onlineScreens = MutableStateFlow(
        (pagedScreenRepository.getScreens() ?: emptyList()).filterIsInstance<OnlineScreenData>()
    )
    override val screens: StateFlow<List<OnlineScreenData>> = _onlineScreens.asStateFlow()

    override fun setScreens(screens: List<PagedScreen<*>>) {
        val onlineScreensData = screens.filterIsInstance<OnlineScreenData>()
        _onlineScreens.value = onlineScreensData
        pagedScreenRepository.setScreens(onlineScreensData)
    }

    override fun getOnlineScreen(screenId: String): OnlineScreenData? {
        return screens.value.find { it.metadata.id == screenId }
    }

    override fun replaceOnlineScreen(screen: OnlineScreenData) {
        screens.value.map { screenData ->
            if (screenData.metadata.id == screen.metadata.id) screen
            else screenData
        }

        pagedScreenRepository.setScreen(screen)
    }
}
