package com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.models.TokenUpdateStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class TokenInteractorImpl(
    private val onlineScreensInteractor: OnlineScreensInteractor,
    private val accountRepository: AccountRepository,
    private val dispatchers: Dispatchers,
) : TokenInteractor {
    private val scope = CoroutineScope(dispatchers.io + SupervisorJob())

    private val _tokenStatus = MutableStateFlow<List<TokenUpdateStatus>>(emptyList())
    override val tokenStatus: StateFlow<List<TokenUpdateStatus>> = _tokenStatus.asStateFlow()

    init {
        scope.launch {
            onlineScreensInteractor.screens.collect { onlineScreens ->
                _tokenStatus.value = onlineScreens.mapNotNull { onlineScreenData ->
                    tokenStatus.value.find { it.accountId == onlineScreenData.accountId }
                        ?: onlineScreenData.accountId?.let { accountId ->
                            TokenUpdateStatus(
                                accountId = accountId,
                                updating = false
                            )
                        }
                }
            }
        }

        scope.launch {
            tokenStatus.first {
                it.isNotEmpty()
            }.let {
                updateTokens()
            }
        }
    }

    override suspend fun updateTokens(): EmptyResult<Error> = withContext(dispatchers.io) {
        val results = _tokenStatus.value.map { tokenUpdateStatus ->
            async {
                updateTokenForAccount(tokenUpdateStatus.accountId)
            }
        }.awaitAll()

        results.firstOrNull { it is Result.Error } ?: Result.Success(Unit)
    }

    @OptIn(ExperimentalTime::class)
    private suspend fun updateTokenForAccount(accountId: Int): EmptyResult<Error> {
        val token = accountRepository.getToken(accountId)
            ?: return Result.Error(DomainErrorType.TokenError)

        if (Instant.fromEpochSeconds(token.expiresAt) - Clock.System.now() > 13.days) {
            updateTokenStatus(accountId, false)
            return Result.Success(Unit)
        }

        val result = accountRepository.getNewToken(token)
        return processTokenUpdateResult(accountId, result)
    }

    private fun processTokenUpdateResult(
        accountId: Int,
        result: Result<Token, Error>
    ): EmptyResult<Error> {
        return when (result) {
            is Result.Success -> {
                accountRepository.setToken(result.data.accountId, result.data)
                Result.Success(Unit)
            }

            is Result.Error -> Result.Error(result.error)
        }.also {
            updateTokenStatus(accountId, false)
        }
    }

    @Suppress("SameParameterValue")
    private fun updateTokenStatus(accountId: Int?, updating: Boolean) {
        _tokenStatus.updateItem(
            predicate = { it.accountId == accountId },
            transform = { it.copy(updating = updating) }
        )
    }
}

private fun <T> MutableStateFlow<List<T>>.updateItem(
    predicate: (T) -> Boolean,
    transform: (T) -> T
) {
    update { list -> list.map { if (predicate(it)) transform(it) else it } }
}
