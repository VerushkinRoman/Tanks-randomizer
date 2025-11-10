package com.posse.tanksrandomizer.common.data.models

import com.posse.tanksrandomizer.common.domain.models.Token
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class NetworkAuthData(
    @SerialName("access_token") val accessToken: String,
    @SerialName("account_id") val accountId: Int,
    @SerialName("expires_at") val expiresAt: Long,
)

fun NetworkAuthData.toToken(): Token {
    return Token(
        accessToken = accessToken,
        accountId = accountId,
        expiresAt = expiresAt,
    )
}
