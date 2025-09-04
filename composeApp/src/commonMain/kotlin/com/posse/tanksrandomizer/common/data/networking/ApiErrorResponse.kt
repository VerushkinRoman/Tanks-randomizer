package com.posse.tanksrandomizer.common.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val code: Int,
    val message: String,
    val field: String?,
    val value: String?,
)
