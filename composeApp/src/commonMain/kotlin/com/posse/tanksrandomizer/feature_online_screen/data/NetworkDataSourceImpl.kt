package com.posse.tanksrandomizer.feature_online_screen.data

import com.posse.tanksrandomizer.common.data.di.ServerConstants.APPLICATION_ID
import com.posse.tanksrandomizer.common.data.models.EncyclopediaTankData
import com.posse.tanksrandomizer.common.data.models.NetworkAccountTank
import com.posse.tanksrandomizer.common.data.models.NetworkEncyclopediaTank
import com.posse.tanksrandomizer.common.data.models.NetworkToken
import com.posse.tanksrandomizer.common.data.networking.EndpointConstants
import com.posse.tanksrandomizer.common.data.networking.safeCall
import com.posse.tanksrandomizer.common.data.util.toAccountTank
import com.posse.tanksrandomizer.common.data.util.toEncyclopediaTank
import com.posse.tanksrandomizer.common.data.util.toToken
import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.utils.map
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class NetworkDataSourceImpl(
    private val httpClient: HttpClient,
    private val accountRepository: AccountRepository,
) : NetworkDataSource {
    override suspend fun logIn(): EmptyResult<Error> {
        val loginURL = LOGIN +
                "?application_id=${APPLICATION_ID}" +
                "&redirect_uri=tanks_randomizer%3A%2F%2Fmain"

        return safeCall<Unit> {
            httpClient.post(urlString = loginURL)
        }
    }

    override suspend fun logOut(): EmptyResult<Error> {
        val token = accountRepository.getToken() ?: return Result.Error(DomainErrorType.TokenError)

        val logoutURL = LOGOUT +
                "?application_id=${APPLICATION_ID}" +
                "&access_token=${token.accessToken}"

        return safeCall<Unit> {
            httpClient.post(urlString = logoutURL)
        }
    }

    override suspend fun getNewToken(): Result<Token, Error> {
        val token = accountRepository.getToken() ?: return Result.Error(DomainErrorType.TokenError)

        val prolongateURL = PROLONGATE +
                "?application_id=${APPLICATION_ID}" +
                "&access_token=${token.accessToken}"

        return safeCall<NetworkToken> {
            httpClient.post(urlString = prolongateURL)
        }.map { response ->
            response.data.toToken()
        }
    }

    override suspend fun getAccountTanks(): Result<List<AccountTank>, Error> {
        val token = accountRepository.getToken() ?: return Result.Error(DomainErrorType.TokenError)

        val accountTanksUrl = STATS +
                "?application_id=${APPLICATION_ID}" +
                "&account_id=${token.accountId}" +
                "&access_token=${token.accessToken}" +
                "&${STATS_PARAMS}"

        return safeCall<NetworkAccountTank> {
            httpClient.get(urlString = accountTanksUrl)
        }.map { response ->
            response.data.values.toList().flatten().map { accountTankData ->
                accountTankData.toAccountTank()
            }
        }
    }

    override suspend fun getEncyclopediaTanks(ids: List<Int>): Result<List<EncyclopediaTank>, Error> {
        return when {
            ids.isEmpty() -> Result.Success(emptyList())
            shouldLoadAllTanks(ids) -> loadAllTanks()
            else -> loadTanksInChunks(ids)
        }
    }

    private fun shouldLoadAllTanks(ids: List<Int>): Boolean {
        return ids.chunked(EndpointConstants.MAX_PARAMETERS_PER_REQUEST).size > MAX_PARALLEL_REQUESTS
    }

    private suspend fun loadAllTanks(): Result<List<EncyclopediaTank>, Error> {
        val allTanksUrl = ENCYCLOPEDIA +
                "?application_id=${APPLICATION_ID}" +
                "&${ENCYCLOPEDIA_PARAMS}"

        return safeCall<NetworkEncyclopediaTank> {
            httpClient.get(urlString = allTanksUrl)
        }
            .map { response ->
                response.data.values.toList().map { encyclopediaTankData ->
                    encyclopediaTankData.toEncyclopediaTank()
                        ?: return Result.Error(DomainErrorType.MapperError)
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

        return safeCall<NetworkEncyclopediaTank> {
            httpClient.get(urlString = tanksByIdUrl)
        }.map { it.data.values.toList() }
    }

    private fun processChunkResults(results: List<Result<List<EncyclopediaTankData>, NetworkError>>): Result<List<EncyclopediaTank>, Error> {
        val (successes, errors) = results.partition { it is Result.Success }

        if (errors.isNotEmpty()) {
            val firstError = errors.first() as Result.Error
            return Result.Error(firstError.error)
        }

        val allTankData = successes
            .filterIsInstance<Result.Success<List<EncyclopediaTankData>>>()
            .flatMap { it.data }

        val encyclopediaTanks = allTankData.map { tankData ->
            tankData.toEncyclopediaTank() ?: return Result.Error(DomainErrorType.MapperError)
        }

        return Result.Success(encyclopediaTanks)
    }

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
