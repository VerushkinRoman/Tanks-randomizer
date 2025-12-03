package com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.PagedOnlineScreenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnlineScreensInteractorImpl(
    private val pagedOnlineScreenRepository: PagedOnlineScreenRepository,
) : OnlineScreensInteractor {
    private val _onlineScreens =
        MutableStateFlow(pagedOnlineScreenRepository.getOnlineScreens() ?: emptyList())
    override val onlineScreens: StateFlow<OnlineScreens> = _onlineScreens.asStateFlow()

    override fun setOnlineScreens(screens: OnlineScreens) {
        _onlineScreens.value = screens
        pagedOnlineScreenRepository.setOnlineScreens(screens)
    }

    override fun getOnlineScreen(screenId: String): OnlineScreenData? {
        return onlineScreens.value.find { it.id == screenId }
    }

    override fun replaceOnlineScreen(screen: OnlineScreenData) {
        onlineScreens.value.map { screenData ->
            if (screenData.id == screen.id) screen
            else screenData
        }

        pagedOnlineScreenRepository.setOnlineScreen(screen)
    }
}
