package com.posse.tanksrandomizer.network.domain.repository

import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.network.domain.models.LoginStatus
import com.posse.tanksrandomizer.network.domain.models.ProlongateData

class AuthRepositoryImpl: AuthRepository {
    override fun login(): Result<LoginStatus, Error> {
        TODO("Not yet implemented")
    }

    override fun logout(): Result<Boolean, Error> {
        TODO("Not yet implemented")
    }

    override fun prolongateAccessToken(accessToken: String): Result<ProlongateData, Error> {
        TODO("Not yet implemented")
    }
}