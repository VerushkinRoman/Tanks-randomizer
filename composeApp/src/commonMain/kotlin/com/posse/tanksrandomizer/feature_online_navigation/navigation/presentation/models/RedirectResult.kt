package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models

import com.posse.tanksrandomizer.common.domain.models.ResponseStatus

sealed class RedirectResult {
    data class Success(val success: SuccessResponse) : RedirectResult()
    data class Error(val error: ErrorResponse) : RedirectResult()
}

data class SuccessResponse(
    val status: String? = null,
    val accessToken: String?,
    val nickname: String?,
    val accountId: Int?,
    val expiresAt: String?,
)

data class ErrorResponse(
    val status: String,
    val message: String?,
    val code: String?,
) {
    companion object {
        val default = ErrorResponse(
            status = ResponseStatus.ERROR.value,
            message = null,
            code = null,
        )
    }
}
