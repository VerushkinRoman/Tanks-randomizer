package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.compose.utils.ErrorHandler.isTokenError
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.OnlineScreensInteractor
import kotlinx.coroutines.withContext

class LogOut(
    private val accountRepository: AccountRepository,
    private val onlineScreensInteractor: OnlineScreensInteractor,
    private val onlineScreenRepository: OnlineScreenRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(screenId: String): EmptyResult<Error> =
        withContext(dispatchers.io) {
            val screenData = onlineScreensInteractor.getOnlineScreen(screenId)
                ?: return@withContext Result.Error(DomainErrorType.TokenError)
            val accountId = screenData.accountId
                ?: return@withContext Result.Error(DomainErrorType.TokenError)
            val token = accountRepository.getToken(accountId)
                ?: return@withContext Result.Error(DomainErrorType.TokenError)

            accountRepository.logOut(token)
                .onSuccess {
                    clear(screenId, screenData)
                }
                .onError { error ->
                    if (error.isTokenError()) {
                        clear(screenId, screenData)
                    }
                }
        }

    private suspend fun clear(screenId: String, screenData: OnlineScreenData) {
        clearAccountId(screenData)
        onlineScreenRepository.clearScreenData(screenId)
        screenData.accountId?.let { accountId ->
            if (onlineScreensInteractor.screens.value.none { it.accountId == accountId }) {
                onlineScreenRepository.clearAccountData(accountId)
                accountRepository.setToken(accountId, null)
            }
        }
    }

    private fun clearAccountId(screenData: OnlineScreenData) {
        onlineScreensInteractor.replaceOnlineScreen(
            screenData.copy(
                additionalData = null
            )
        )
    }
}
