package com.posse.tanksrandomizer.common.data.networking

import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (_: UnresolvedAddressException) {
        return Result.Error(NetworkError.NoInternet)
    } catch (_: SerializationException) {
        return Result.Error(NetworkError.Serialization)
    } catch (_: Exception) {
        currentCoroutineContext().ensureActive()
        return Result.Error(NetworkError.Unknown)
    }

    return responseToResult(response)
}
