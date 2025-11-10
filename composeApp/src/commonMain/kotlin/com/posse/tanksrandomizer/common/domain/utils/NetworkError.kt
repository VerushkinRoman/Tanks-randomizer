package com.posse.tanksrandomizer.common.domain.utils

sealed interface NetworkError : Error {
    object ApiError : NetworkError
    object NoInternet : NetworkError
    object ServerError : NetworkError
    object Serialization : NetworkError
    object SourceNotAvailable : NetworkError
    object Unknown : NetworkError

    // API-specific errors
    data class FieldNotSpecified(val field: String) : NetworkError
    data class FieldNotFound(val field: String) : NetworkError
    object MethodNotFound : NetworkError
    object MethodDisabled : NetworkError
    data class FieldListLimitExceeded(val field: String) : NetworkError
    object ApplicationIsBlocked : NetworkError
    object InvalidApplicationId : NetworkError
    object InvalidIpAddress : NetworkError
    object RequestLimitExceeded : NetworkError
    data class InvalidField(val field: String) : NetworkError
}
