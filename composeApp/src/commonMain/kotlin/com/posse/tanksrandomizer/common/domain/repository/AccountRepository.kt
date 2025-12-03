package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result

interface AccountRepository {
    fun getToken(accountId: Int): Token?
    fun setToken(accountId: Int, token: Token?)

    suspend fun getNewToken(token: Token): Result<Token, Error>

    suspend fun logIn(): Result<String, Error>
    suspend fun logOut(token: Token): EmptyResult<Error>
}
