package com.posse.tanksrandomizer.common.domain.utils

enum class NetworkError : Error {
    API_ERROR,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    SOURCE_NOT_AVAILABLE,
    UNKNOWN,
}