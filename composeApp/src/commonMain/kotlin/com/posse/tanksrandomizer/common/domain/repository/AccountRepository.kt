package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result

interface AccountRepository {
    suspend fun logIn(): Result<String, Error>
    suspend fun logOut(accountId: Int): EmptyResult<Error>
    fun clearToken(accountId: Int)
    fun setNewToken(token: Token)
}
