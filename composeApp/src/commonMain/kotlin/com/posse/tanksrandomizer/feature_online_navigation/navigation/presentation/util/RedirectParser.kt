package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.util

import com.posse.tanksrandomizer.common.domain.models.ErrorResponseStatus
import com.posse.tanksrandomizer.common.domain.models.ResponseStatus
import com.posse.tanksrandomizer.common.domain.models.SuccessResponseParams
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.RedirectResult
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.SuccessResponse
import kotlinx.serialization.json.Json

object RedirectParser {
    fun parse(uri: String?): RedirectResult? {
        uri ?: return getError(null)

        return try {
            val params = uri.split("&").associate {
                val parts = it.split("=")
                parts[0] to parts.getOrNull(1)?.decodeURLPart()
            }

            when (params[ResponseStatus.STATUS.value]) {
                ResponseStatus.OK.value -> {
                    RedirectResult.Success(
                        SuccessResponse(
                            status = params[ResponseStatus.STATUS.value],
                            accessToken = params[SuccessResponseParams.ACCESS_TOKEN.value],
                            nickname = params[SuccessResponseParams.NICKNAME.value],
                            accountId = params[SuccessResponseParams.ACCOUNT_ID.value]?.toIntOrNull(),
                            expiresAt = params[SuccessResponseParams.EXPIRES_AT.value],
                        )
                    )
                }

                ResponseStatus.ERROR.value -> getError(params)

                else -> getError(params)
            }
        } catch (_: Exception) {
            getError(mapOf(Pair(ErrorResponseStatus.MESSAGE.value, uri)))
        }
    }

    private fun getError(params: Map<String, String?>?): RedirectResult.Error? {
        return params?.let {
            RedirectResult.Error(
                ErrorResponse(
                    status = params[ResponseStatus.STATUS.value] ?: ResponseStatus.ERROR.value,
                    message = params[ErrorResponseStatus.MESSAGE.value],
                    code = params[ErrorResponseStatus.CODE.value],
                )
            )
        }
    }

    private fun String.decodeURLPart(): String {
        return Json.decodeFromString("\"$this\"")
    }
}
