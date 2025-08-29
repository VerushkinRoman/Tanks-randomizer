package com.posse.tanksrandomizer.feature_online_screen.data

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank

interface NetworkDataSource {
    suspend fun logIn(): EmptyResult<Error>
    suspend fun logOut(): EmptyResult<Error>
    suspend fun getNewToken(): Result<Token, Error>
    suspend fun getAccountTanks(): Result<List<AccountTank>, Error>
    suspend fun getEncyclopediaTanks(ids: List<Int>): Result<List<EncyclopediaTank>, Error>
}
