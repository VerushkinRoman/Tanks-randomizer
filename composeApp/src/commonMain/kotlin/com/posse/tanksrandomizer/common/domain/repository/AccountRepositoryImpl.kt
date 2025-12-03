package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSource
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result

class AccountRepositoryImpl(
    private val offlineDataSource: OfflineDataSource,
    private val onlineDataSource: OnlineDataSource,
) : AccountRepository {
    override fun getToken(accountId: Int): Token? = offlineDataSource.getToken(accountId)
    override fun setToken(accountId: Int, token: Token?) = offlineDataSource.setToken(accountId, token)

    override suspend fun getNewToken(token: Token): Result<Token, Error> {
        return onlineDataSource.getNewToken(token)
    }

    override suspend fun logIn(): Result<String, Error> = onlineDataSource.logIn()
    override suspend fun logOut(token: Token): EmptyResult<Error> {
        return onlineDataSource.logOut(token)
    }
}
