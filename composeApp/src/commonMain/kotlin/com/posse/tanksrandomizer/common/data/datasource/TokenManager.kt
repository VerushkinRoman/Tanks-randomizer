package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.data.networking.NetworkChecker
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result

interface TokenManager {
    val networkChecker: NetworkChecker

    suspend fun getToken(
        accountId: Int,
        backgroundUpdate: Boolean = false,
        networkQuality: NetworkChecker.NetworkQuality = networkChecker.checkNetworkQuality()
    ): Result<Token, Error>

    suspend fun updateAllTokens(backgroundUpdate: Boolean = false)

    fun setNewToken(token: Token)
    fun clearToken(accountId: Int)
    fun getCurrentTokenExpiration(accountId: Int): Long?
}
