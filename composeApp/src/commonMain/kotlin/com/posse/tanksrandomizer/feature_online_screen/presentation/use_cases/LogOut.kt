package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import kotlinx.coroutines.withContext

class LogOut(
    private val accountRepository: AccountRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(): EmptyResult<Error> = withContext(dispatchers.io) {
        accountRepository.logOut().onSuccess { accountRepository.setToken(null) }
    }
}
