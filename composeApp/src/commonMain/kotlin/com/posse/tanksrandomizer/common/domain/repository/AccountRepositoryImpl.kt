package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSource

class AccountRepositoryImpl(
    private val offlineDataSource: OfflineDataSource,
    private val onlineDataSource: OnlineDataSource,
) : AccountRepository {
    override fun getToken(): Token? = offlineDataSource.getToken()
    override fun setToken(token: Token?) = offlineDataSource.setToken(token)

    override fun getNickname(): String? = offlineDataSource.getNickname()
    override fun setNickname(nickname: String?) = offlineDataSource.setNickname(nickname)

    override suspend fun getNewToken(): Result<Token, Error> {
        val token = offlineDataSource.getToken() ?: return Result.Error(DomainErrorType.TokenError)
        return onlineDataSource.getNewToken(token)
    }

    override suspend fun logIn(): Result<String ,Error> = onlineDataSource.logIn()
    override suspend fun logOut(): EmptyResult<Error> {
        val token = offlineDataSource.getToken() ?: return Result.Error(DomainErrorType.TokenError)
        return onlineDataSource.logOut(token)
    }
}
