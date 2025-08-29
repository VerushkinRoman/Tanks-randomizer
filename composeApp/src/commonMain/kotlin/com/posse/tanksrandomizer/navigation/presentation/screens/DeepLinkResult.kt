package com.posse.tanksrandomizer.navigation.presentation.screens

import com.posse.tanksrandomizer.common.domain.models.ResponseStatus
import kotlinx.serialization.Serializable

sealed class DeepLinkResult {
    data class Success(val success: SuccessResponse) : DeepLinkResult()
    data class Error(val error: ErrorResponse) : DeepLinkResult()
}

@Serializable
data class SuccessResponse(
    val status: String?,
    val accessToken: String?,
    val nickname: String?,
    val accountId: String?,
    val expiresAt: String?,
)

@Serializable
data class ErrorResponse(
    val status: String,
    val message: String?,
    val code: String?,
) {
    companion object{
        val default = ErrorResponse(
            status = ResponseStatus.ERROR.value,
            message = null,
            code = null,
        )
    }
}
