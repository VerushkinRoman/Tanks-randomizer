package com.posse.tanksrandomizer.feature_online_pane.data

import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Token

interface NetworkDataSource {
    suspend fun logIn(): EmptyResult<NetworkError>
    suspend fun logOut(accessToken: String): EmptyResult<NetworkError>
    suspend fun getNewToken(accessToken: String): Result<Token, NetworkError>
    suspend fun getTanks(): Result<List<Tank>, NetworkError>
}