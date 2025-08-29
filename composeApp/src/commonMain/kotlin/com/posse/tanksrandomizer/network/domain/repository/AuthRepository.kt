package com.posse.tanksrandomizer.network.domain.repository

import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.network.domain.models.LoginStatus
import com.posse.tanksrandomizer.network.domain.models.ProlongateData

interface AuthRepository {
    fun login(): Result<LoginStatus, Error>
    fun logout(): Result<Boolean, Error>
    fun prolongateAccessToken(accessToken: String): Result<ProlongateData, Error>
}