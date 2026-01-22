package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.data.di.ServerConstants.APPLICATION_ID
import com.posse.tanksrandomizer.common.data.models.NetworkAuthData
import com.posse.tanksrandomizer.common.data.models.toToken
import com.posse.tanksrandomizer.common.data.networking.EndpointConstants
import com.posse.tanksrandomizer.common.data.networking.NetworkChecker
import com.posse.tanksrandomizer.common.data.networking.safeCall
import com.posse.tanksrandomizer.common.data.utils.SingleFlight
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.utils.map
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import com.posse.tanksrandomizer.common.paged_screens_navigation.data.datasource.PagedScreenDataSource
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

@OptIn(ExperimentalSettingsApi::class, ExperimentalSerializationApi::class)
class TokenManagerImpl(
    private val settings: Settings,
    private val httpClient: HttpClient,
    private val pagedScreenDataSource: PagedScreenDataSource,
    override val networkChecker: NetworkChecker,
    private val dispatchers: Dispatchers,
) : TokenManager {
    private val singleFlight = SingleFlight<Result<Token, Error>>()

    override suspend fun getToken(
        accountId: Int,
        backgroundUpdate: Boolean,
        networkQuality: NetworkChecker.NetworkQuality,
    ): Result<Token, Error> {
        val token = getCurrentToken(accountId)
            ?: return Result.Error(DomainErrorType.NoTokensInRepo)

        return if (shouldUpdate(token.expiresAt, backgroundUpdate, networkQuality)) {
            getNewToken(token).also { result ->
                result.onSuccess { newToken ->
                    setNewToken(newToken)
                }
            }
        } else Result.Success(token)
    }

    override suspend fun updateAllTokens(backgroundUpdate: Boolean) {
        supervisorScope {
            val networkQuality = networkChecker.checkNetworkQuality()
            pagedScreenDataSource
                .getAccounts()
                ?.ifEmpty { return@supervisorScope }
                ?.forEach {
                    launch {
                        getToken(
                            accountId = it,
                            backgroundUpdate = backgroundUpdate,
                            networkQuality = networkQuality,
                        )
                    }
                }
        }
    }

    override fun clearToken(accountId: Int) = setToken(accountId, null)

    override fun getCurrentTokenExpiration(accountId: Int): Long? {
        return getCurrentToken(accountId)?.expiresAt
    }

    override fun setNewToken(token: Token) = setToken(token.accountId, token)

    private fun setToken(accountId: Int, token: Token?) {
        settings.encodeValue("${TOKEN_KEY}_$accountId", value = token)
    }

    private fun getCurrentToken(accountId: Int): Token? {
        return settings.decodeValueOrNull<Token>("${TOKEN_KEY}_$accountId")
    }

    private suspend fun getNewToken(oldToken: Token): Result<Token, Error> {
        return singleFlight.joinOrRun(oldToken.accessToken) {
            withContext(NonCancellable) {
                withContext(dispatchers.io) {
                    getNewTokenFromApi(oldToken)
                }
            }
        }
    }

    private suspend fun getNewTokenFromApi(oldToken: Token): Result<Token, Error> {
        return safeCall<NetworkAuthData> {
            httpClient.post(urlString = PROLONGATE) {
                setBody(
                    FormDataContent(Parameters.build {
                        append("application_id", APPLICATION_ID)
                        append("access_token", oldToken.accessToken)
                    })
                )
            }
        }.map { response ->
            response.toToken()
        }
    }

    private fun shouldUpdate(
        expiresAt: Long,
        backgroundUpdate: Boolean,
        networkQuality: NetworkChecker.NetworkQuality
    ): Boolean {
        val now = Clock.System.now()
        val expires = Instant.fromEpochSeconds(expiresAt)
        val daysLeft = expires - now
        return (backgroundUpdate && daysLeft < 13.days && networkQuality != NetworkChecker.NetworkQuality.POOR) ||
                (daysLeft < 7.days && networkQuality != NetworkChecker.NetworkQuality.POOR) ||
                daysLeft < 2.days
    }

    companion object {
        private const val TOKEN_KEY = "token"
        const val PROLONGATE = EndpointConstants.AUTH_PATH + "prolongate/"
    }
}
