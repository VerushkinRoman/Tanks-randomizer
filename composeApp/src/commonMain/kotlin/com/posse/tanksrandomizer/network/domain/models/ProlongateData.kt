package com.posse.tanksrandomizer.network.domain.models

import kotlinx.datetime.LocalDateTime

class ProlongateData(
    val accessToken: String,
    val accountID: Int,
    val expiresAt: LocalDateTime,
)