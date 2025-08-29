package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import kotlinx.coroutines.withContext

class UpdateToken(
    private val accountRepository: AccountRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(): Result<Token, Error> {
        return withContext(dispatchers.io) {
            accountRepository.getNewToken()
                .onSuccess { accountRepository.setToken(it) }
        }
    }
}