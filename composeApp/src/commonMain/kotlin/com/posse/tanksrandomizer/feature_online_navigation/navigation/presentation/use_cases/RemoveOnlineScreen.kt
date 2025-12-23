package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.RemoveScreen
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository

class RemoveOnlineScreen(
    private val accountRepository: AccountRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
) : RemoveScreen() {
    override operator fun invoke(
        id: String,
        screens: List<PagedScreen<*>>
    ): List<OnlineScreenData> {
        @Suppress("UNCHECKED_CAST")
        screens as List<OnlineScreenData>

        val deletedScreen = screens.find { it.metadata.id == id } ?: return screens
        val remainingScreens = screens - deletedScreen

        deletedScreen.accountId?.let { accountId ->
            if (remainingScreens.none { it.accountId == accountId }) {
                accountRepository.setToken(accountId, null)
            }
        }

        onlineScreenRepository.clearScreenData(id)
        val selectedIndex = maxOf(0, deletedScreen.metadata.position - 1)
        return remainingScreens.mapIndexed { index, screen ->
            val metadata = screen.metadata.copy(
                position = index,
                selected = if (deletedScreen.metadata.selected) index == selectedIndex else screen.metadata.selected
            )
            screen.withMetadata(metadata)
        }
    }
}
