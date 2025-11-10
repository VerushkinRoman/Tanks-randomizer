package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import kotlinx.coroutines.withContext

class LogInToAccount(
    private val accountRepository: AccountRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(): Result<String, Error> = withContext(dispatchers.io) {
        accountRepository.logIn()
    }
}
