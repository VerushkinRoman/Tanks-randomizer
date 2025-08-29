package com.posse.tanksrandomizer.common.data.networking

data class ApiErrorResponse(
    val code: Int,
    val message: String,
    val field: String?,
    val value: Any?
)
