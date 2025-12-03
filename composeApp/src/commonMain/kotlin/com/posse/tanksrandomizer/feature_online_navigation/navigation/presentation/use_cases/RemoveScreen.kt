package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository

class RemoveScreen(
    private val accountRepository: AccountRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
) {
    operator fun invoke(id: String, screens: OnlineScreens): OnlineScreens {
        val deletedScreen = screens.find { it.id == id } ?: return screens
        val remainingScreens = screens - deletedScreen

        deletedScreen.accountId?.let { accountId ->
            if (remainingScreens.none { it.accountId == accountId }) {
                accountRepository.setToken(accountId, null)
            }
        }

        onlineScreenRepository.clearScreenData(id)
        val selectedIndex = maxOf(0, deletedScreen.position - 1)
        return remainingScreens.mapIndexed { index, screen ->
            screen.copy(
                position = index,
                selected = if (deletedScreen.selected) index == selectedIndex else screen.selected
            )
        }
    }
}
