package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData

class SetSuccessLoginResult(
    private val accountRepository: AccountRepository,
) {
    operator fun invoke(
        screenId: String,
        name: String,
        token: Token,
        screens: List<OnlineScreenData>
    ): List<OnlineScreenData> {
        return screens.map { screenData ->
            if (screenData.metadata.id == screenId) {
                screenData.copy(
                    metadata = screenData.metadata.copy(
                        name = name
                    ),
                    additionalData = token.accountId
                ).also {
                    accountRepository.setNewToken(token)
                }
            } else screenData
        }
    }
}
