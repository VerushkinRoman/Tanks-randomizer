package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.OfflineCommonDataSource
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_screen.data.NetworkDataSource

class AccountRepositoryImpl(
    private val offlineCommonDataSource: OfflineCommonDataSource,
    private val networkDataSource: NetworkDataSource,
) : AccountRepository {
    override fun getToken(): Token? = offlineCommonDataSource.getToken()
    override fun setToken(token: Token?) = offlineCommonDataSource.setToken(token)

    override suspend fun getNewToken(): Result<Token, Error> = networkDataSource.getNewToken()

    override suspend fun logIn(): EmptyResult<Error> = networkDataSource.logIn()
    override suspend fun logOut(): EmptyResult<Error> = networkDataSource.logOut()
}
