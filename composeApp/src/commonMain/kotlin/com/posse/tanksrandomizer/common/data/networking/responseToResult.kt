package com.posse.tanksrandomizer.common.data.networking

import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return try {
        val json = response.body<String>()
        val parsed = Json.decodeFromString<JsonObject>(json)

        when (parsed["status"]?.jsonPrimitive?.contentOrNull) {
            "ok" -> {
                val data = parsed["data"]?.let { Json.decodeFromJsonElement<T>(it) }
                    ?: Unit as T
                Result.Success(data)
            }

            "error" -> {
                val error = Json.decodeFromJsonElement<ApiErrorResponse>(parsed["error"]!!)
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
                error.message.removePrefix("INVALID_").lowercase()
            )
            error.message.endsWith("_LIST_LIMIT_EXCEEDED") -> NetworkError.FieldListLimitExceeded(
                error.message.removeSuffix("_LIST_LIMIT_EXCEEDED").lowercase()
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
