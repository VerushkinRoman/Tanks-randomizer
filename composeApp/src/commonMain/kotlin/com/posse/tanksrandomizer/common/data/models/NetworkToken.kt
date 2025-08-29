package com.posse.tanksrandomizer.common.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class NetworkToken(
    val status: String,
    val meta: TokenMetaData,
    val data: NetworkAuthData,
)

@Serializable
data class TokenMetaData(
    val count: Int,
)

@Serializable
data class NetworkAuthData(
    @SerialName("access_token") val accessToken: String,
    @SerialName("account_id") val accountId: Int,
    @SerialName("expires_at") val expiresAt: Long,
)
