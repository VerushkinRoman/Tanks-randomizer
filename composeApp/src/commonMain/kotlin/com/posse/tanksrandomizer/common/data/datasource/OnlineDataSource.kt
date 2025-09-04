package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result

interface OnlineDataSource {
    suspend fun logIn(): Result<String ,Error>
    suspend fun logOut(currentToken: Token): EmptyResult<Error>
    suspend fun getNewToken(currentToken: Token): Result<Token, Error>
    suspend fun getAccountTanks(currentToken: Token): Result<List<AccountTank>, Error>
    suspend fun getEncyclopediaTanks(
        ids: List<Int>,
        currentToken: Token
    ): Result<List<EncyclopediaTank>, Error>
}
