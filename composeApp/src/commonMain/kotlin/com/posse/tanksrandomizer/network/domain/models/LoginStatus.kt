package com.posse.tanksrandomizer.network.domain.models

import kotlinx.datetime.LocalDateTime

sealed interface LoginStatus {
    class LoginOK(
        val accessToken: String,
        val nickname: String,
        val accountID: Int,
        val expiresAt: LocalDateTime,
    ) : LoginStatus

    class LoginError(
        val code: Int,
        val message: String,
    ) : LoginStatus
}