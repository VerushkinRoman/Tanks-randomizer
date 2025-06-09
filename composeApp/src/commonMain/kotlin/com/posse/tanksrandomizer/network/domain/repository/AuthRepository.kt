package com.posse.tanksrandomizer.network.domain.repository

import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.network.domain.models.LoginStatus
import com.posse.tanksrandomizer.network.domain.models.ProlongateData

interface AuthRepository {
    fun login(): Result<LoginStatus, NetworkError>
    fun logout(): Result<Boolean, NetworkError>
    fun prolongateAccessToken(accessToken: String): Result<ProlongateData, NetworkError>
}