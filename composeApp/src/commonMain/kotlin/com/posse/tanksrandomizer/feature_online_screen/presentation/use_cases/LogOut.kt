package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.NetworkError.Companion.isTokenError
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineScreenRepository
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

class LogOut(
    private val accountRepository: AccountRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(): EmptyResult<Error> = withContext(dispatchers.io) {
        accountRepository.logOut()
            .onSuccess {
                accountRepository.setToken(null)
                onlineScreenRepository.setTanksInGarage(emptyList())
                @OptIn(ExperimentalTime::class)
                onlineScreenRepository.setLastAccountUpdated(null)
                onlineScreenRepository.setSelectedTank(null)
            }
            .onError { error ->
                if (error.isTokenError()) {
                    accountRepository.setToken(null)
                }
            }
    }
}
