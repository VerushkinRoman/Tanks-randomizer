package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result

interface AccountRepository {
    fun getToken(): Token?
    fun setToken(token: Token?)

    fun getNickname(): String?
    fun setNickname(nickname: String?)

    suspend fun logIn(): Result<String ,Error>
    suspend fun logOut(): EmptyResult<Error>
    suspend fun getNewToken(): Result<Token, Error>
}
