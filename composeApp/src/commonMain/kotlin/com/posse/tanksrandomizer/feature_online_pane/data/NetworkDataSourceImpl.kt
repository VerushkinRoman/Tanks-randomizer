package com.posse.tanksrandomizer.feature_online_pane.data

import com.posse.tanksrandomizer.common.data.di.ServerConstants.APPLICATION_ID
import com.posse.tanksrandomizer.common.data.networking.safeCall
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Token
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class NetworkDataSourceImpl(
    private val httpClient: HttpClient,
) : NetworkDataSource {
    override suspend fun logIn(): EmptyResult<NetworkError> {
        return safeCall<Unit> {
            httpClient.post(
                urlString = "https://api.tanki.su/wot/auth/login/?application_id=${APPLICATION_ID}?redirect_uri=https%3A%2F%2Fapi.tanki.su%2Fwot%2F%2Fblank%2F" // TODO
            )
        }
    }

    override suspend fun logOut(accessToken: String): EmptyResult<NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun getNewToken(accessToken: String): Result<Token, NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun getTanks(): Result<List<Tank>, NetworkError> {
        TODO("Not yet implemented")
    }

}