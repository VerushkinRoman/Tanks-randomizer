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
    @Suppress("UNCHECKED_CAST")
    private val _onlineScreens = MutableStateFlow(
        (pagedScreenRepository.getScreens() ?: emptyList()) as List<OnlineScreenData>
    )
    override val screens: StateFlow<List<OnlineScreenData>> = _onlineScreens.asStateFlow()

    override fun setScreens(screens: List<PagedScreen<*>>) {
        @Suppress("UNCHECKED_CAST")
        screens as List<OnlineScreenData>

        _onlineScreens.value = screens
        pagedScreenRepository.setScreens(screens)
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
