package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens

class SetSuccessLoginResult(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(
        screenId: String,
        name: String,
        token: Token,
        screens: OnlineScreens
    ): OnlineScreens {
        return screens.map { screenData ->
            if (screenData.id == screenId) {
                screenData.copy(
                    name = name,
                    accountId = token.accountId
                ).also {
                    accountRepository.setToken(token.accountId, token)
                }
            } else screenData
        }
    }
}
