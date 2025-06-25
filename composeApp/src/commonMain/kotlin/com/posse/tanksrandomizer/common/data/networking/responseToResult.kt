package com.posse.tanksrandomizer.common.data.networking

import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (_: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }

        402, 404, 405, 407 -> Result.Error(NetworkError.API_ERROR)
        504 -> Result.Error(NetworkError.SOURCE_NOT_AVAILABLE)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}
