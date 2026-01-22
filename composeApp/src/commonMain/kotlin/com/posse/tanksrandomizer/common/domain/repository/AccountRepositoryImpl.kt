package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSource
import com.posse.tanksrandomizer.common.data.datasource.TokenManager
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result

class AccountRepositoryImpl(
    private val onlineDataSource: OnlineDataSource,
    private val tokenManager: TokenManager,
) : AccountRepository {
    override suspend fun logIn(): Result<String, Error> = onlineDataSource.logIn()
    override suspend fun logOut(accountId: Int): EmptyResult<Error> = onlineDataSource.logOut(accountId)
    override fun clearToken(accountId: Int) = tokenManager.clearToken(accountId)
    override fun setNewToken(token: Token) = tokenManager.setNewToken(token)
}
