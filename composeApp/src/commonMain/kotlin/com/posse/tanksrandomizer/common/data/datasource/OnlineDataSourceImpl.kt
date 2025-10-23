package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.data.di.ServerConstants.APPLICATION_ID
import com.posse.tanksrandomizer.common.data.models.EncyclopediaTankData
import com.posse.tanksrandomizer.common.data.models.NetworkAccountTankResponse
import com.posse.tanksrandomizer.common.data.models.NetworkAuthData
import com.posse.tanksrandomizer.common.data.models.NetworkEncyclopediaTankData
import com.posse.tanksrandomizer.common.data.networking.EmptyData
import com.posse.tanksrandomizer.common.data.networking.EndpointConstants
import com.posse.tanksrandomizer.common.data.networking.EndpointConstants.REDIRECT_URL
import com.posse.tanksrandomizer.common.data.networking.safeCall
import com.posse.tanksrandomizer.common.data.util.toAccountTank
import com.posse.tanksrandomizer.common.data.util.toEncyclopediaTank
import com.posse.tanksrandomizer.common.data.util.toToken
import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.utils.asEmptyDataResult
import com.posse.tanksrandomizer.common.domain.utils.map
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class OnlineDataSourceImpl(
    private val httpClient: HttpClient,
) : OnlineDataSource {
    override suspend fun logIn(): Result<String, Error> {
        val loginURL = LOGIN +
                "?application_id=${APPLICATION_ID}" +
                "&redirect_uri=${REDIRECT_URL}"

        return safeCall<String> {
            httpClient.post(urlString = loginURL)
        }
    }

    override suspend fun logOut(currentToken: Token): EmptyResult<Error> {
        return safeCall<EmptyData> {
            httpClient.post(urlString = LOGOUT) {
                setBody(
                    FormDataContent(Parameters.build {
                        append("application_id", APPLICATION_ID)
                        append("access_token", currentToken.accessToken)
                    })
                )
            }
        }.asEmptyDataResult()
    }

    override suspend fun getNewToken(currentToken: Token): Result<Token, Error> {
        return safeCall<NetworkAuthData> {
            httpClient.post(urlString = PROLONGATE) {
                setBody(
                    FormDataContent(Parameters.build {
                        append("application_id", APPLICATION_ID)
                        append("access_token", currentToken.accessToken)
                    })
                )
            }
        }.map { response ->
            response.toToken()
        }
    }

    override suspend fun getAccountTanks(currentToken: Token): Result<List<AccountTank>, Error> {
        val accountTanksUrl = STATS +
                "?application_id=${APPLICATION_ID}" +
                "&account_id=${currentToken.accountId}" +
                "&access_token=${currentToken.accessToken}" +
                "&${STATS_PARAMS}"

        return safeCall<NetworkAccountTankResponse> {
            httpClient.get(urlString = accountTanksUrl)
        }.map { response ->
            response.values.flatten()
                .map { networkAccountTank -> networkAccountTank.toAccountTank() }
        }
    }

    override suspend fun getEncyclopediaTanks(
        ids: List<Int>,
        currentToken: Token
    ): Result<List<EncyclopediaTank>, Error> = when {
        ids.isEmpty() -> Result.Success(emptyList())
        shouldLoadAllTanks(ids) -> loadAllTanks()
        else -> loadTanksInChunks(ids)
    }

    private fun shouldLoadAllTanks(ids: List<Int>): Boolean {
        return ids.chunked(EndpointConstants.MAX_PARAMETERS_PER_REQUEST).size > MAX_PARALLEL_REQUESTS
    }

    private suspend fun loadAllTanks(): Result<List<EncyclopediaTank>, Error> {
        val allTanksUrl = ENCYCLOPEDIA +
                "?application_id=${APPLICATION_ID}" +
                "&${ENCYCLOPEDIA_PARAMS}"

        return safeCall<NetworkEncyclopediaTankData> {
            httpClient.get(urlString = allTanksUrl)
        }
            .map { response ->
                response.values.mapNotNull { encyclopediaTankData ->
                    encyclopediaTankData?.toEncyclopediaTank()
                }
            }
    }

    private suspend fun loadTanksInChunks(ids: List<Int>): Result<List<EncyclopediaTank>, Error> {
        return coroutineScope {
            val chunks = ids.chunked(EndpointConstants.MAX_PARAMETERS_PER_REQUEST)

            val deferredResults = chunks.map { chunk ->
                async { loadTankChunk(chunk) }
            }

            val results = deferredResults.awaitAll()

            processChunkResults(results)
        }
    }

    private suspend fun loadTankChunk(ids: List<Int>): Result<List<EncyclopediaTankData>, NetworkError> {
        val tankIds = ids.joinToString("%2C")

        val tanksByIdUrl = ENCYCLOPEDIA +
                "?tank_id=${tankIds}" +
                "&application_id=${APPLICATION_ID}" +
                "&${ENCYCLOPEDIA_PARAMS}"

        return safeCall<NetworkEncyclopediaTankData> {
            httpClient.get(urlString = tanksByIdUrl)
        }.map { it.values.filterNotNull() }
    }

    private fun processChunkResults(
        results: List<Result<List<EncyclopediaTankData>, NetworkError>>
    ): Result<List<EncyclopediaTank>, Error> {
        val (successes, errors) = results.partition { it is Result.Success }

        if (errors.isNotEmpty()) {
            val firstError = errors.first() as Result.Error
            return Result.Error(firstError.error)
        }

        val allTankData = successes
            .filterIsInstance<Result.Success<List<EncyclopediaTankData>>>()
            .flatMap { it.data }

        val encyclopediaTanks = allTankData.map { tankData ->
            tankData.toEncyclopediaTank()
        }

        return Result.Success(encyclopediaTanks)
    }

    @Suppress("SpellCheckingInspection")
    private companion object {
        const val MAX_PARALLEL_REQUESTS = EndpointConstants.MAX_REQUESTS_PER_SECONDS - 2
        const val LOGIN = EndpointConstants.AUTH_PATH + "login/"
        const val LOGOUT = EndpointConstants.AUTH_PATH + "logout/"
        const val PROLONGATE = EndpointConstants.AUTH_PATH + "prolongate/"
        const val STATS = EndpointConstants.BLITZ_PATH + "tanks/stats/"
        const val ENCYCLOPEDIA = EndpointConstants.BLITZ_PATH + "encyclopedia/vehicles/"
        const val ENCYCLOPEDIA_PARAMS = "fields=name%2Cis_premium%2Cnation%2Ctank_id%2Ctier%2Ctype%2Cimages.preview"
        const val STATS_PARAMS = "in_garage=1&fields=mark_of_mastery%2C+tank_id"
    }
}
