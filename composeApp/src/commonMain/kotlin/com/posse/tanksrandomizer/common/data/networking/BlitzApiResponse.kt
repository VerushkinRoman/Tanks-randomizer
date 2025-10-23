package com.posse.tanksrandomizer.common.data.networking

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class BlitzApiResponse(
    val status: String,
    val meta: Meta? = null,
    val data: JsonElement? = null,
    val error: ApiErrorResponse? = null,
)

@Serializable
data class Meta(
    val count: Int? = null
)

@Serializable
data class EmptyData(val dummy: String? = null)
