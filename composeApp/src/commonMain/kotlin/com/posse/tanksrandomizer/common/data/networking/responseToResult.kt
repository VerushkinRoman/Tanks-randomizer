package com.posse.tanksrandomizer.common.data.networking

import com.posse.tanksrandomizer.common.data.networking.EndpointConstants.REDIRECT_URL_ENCODED
import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return try {
        try {
            response.headers["Location"]
        } catch (_: Exception) {
            null
        }?.let { redirectUrl ->
            if (redirectUrl.contains(REDIRECT_URL_ENCODED)) {
                return Result.Success(redirectUrl as T)
            }
        }

        val json = response.body<String>()
        val parsed = Json.decodeFromString<JsonObject>(json)
        val decodedResponse = Json.decodeFromJsonElement<BlitzApiResponse>(parsed)

        when (decodedResponse.status) {
            "ok" -> {
                val data = decodedResponse.data?.let { responseData ->
                    Json.decodeFromJsonElement<T>(responseData) as T
                } ?: return Result.Error(NetworkError.Serialization)
                Result.Success(data)
            }

            "error" -> {
                val error = decodedResponse.error ?: return Result.Error(NetworkError.Unknown)
                Result.Error(parseApiError(error))
            }

            else -> Result.Error(NetworkError.Unknown)
        }
    } catch (_: NoTransformationFoundException) {
        Result.Error(NetworkError.Serialization)
    } catch (_: Exception) {
        handleHttpStatus(response.status.value)
    }
}

fun parseApiError(error: ApiErrorResponse): NetworkError {
    return when (error.code) {
        402 -> when (error.message) {
            "APPLICATION_ID_NOT_SPECIFIED" -> NetworkError.InvalidApplicationId
            else -> NetworkError.FieldNotSpecified(error.field ?: "unknown")
        }

        404 -> when (error.message) {
            "METHOD_NOT_FOUND" -> NetworkError.MethodNotFound
            else -> NetworkError.FieldNotFound(error.field ?: "unknown")
        }

        405 -> NetworkError.MethodDisabled

        407 -> when {
            error.message == "APPLICATION_IS_BLOCKED" -> NetworkError.ApplicationIsBlocked
            error.message == "INVALID_APPLICATION_ID" -> NetworkError.InvalidApplicationId
            error.message == "INVALID_IP_ADDRESS" -> NetworkError.InvalidIpAddress
            error.message == "REQUEST_LIMIT_EXCEEDED" -> NetworkError.RequestLimitExceeded
            error.message.startsWith("INVALID_") -> NetworkError.InvalidField(
                "${error.field} - ${error.value}"
            )

            error.message.endsWith("_LIST_LIMIT_EXCEEDED") -> NetworkError.FieldListLimitExceeded(
                "${error.field} - ${error.value}"
            )

            else -> NetworkError.ApiError
        }

        504 -> NetworkError.SourceNotAvailable
        else -> NetworkError.Unknown
    }
}

fun handleHttpStatus(status: Int): Result.Error<NetworkError> {
    return when (status) {
        in 200..299 -> Result.Error(NetworkError.Unknown) // Shouldn't happen
        402, 404, 405, 407 -> Result.Error(NetworkError.ApiError)
        504 -> Result.Error(NetworkError.SourceNotAvailable)
        in 500..599 -> Result.Error(NetworkError.ServerError)
        else -> Result.Error(NetworkError.Unknown)
    }
}
