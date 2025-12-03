package com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor

import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.models.TokenUpdateStatus
import kotlinx.coroutines.flow.StateFlow

interface TokenInteractor {
    val tokenStatus: StateFlow<List<TokenUpdateStatus>>
    suspend fun updateTokens(): EmptyResult<Error>
}
