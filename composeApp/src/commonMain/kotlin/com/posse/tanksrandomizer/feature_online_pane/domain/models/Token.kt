package com.posse.tanksrandomizer.feature_online_pane.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val accessToken: String,
    val accountId: Int,
    val expiresAt: Long,
)