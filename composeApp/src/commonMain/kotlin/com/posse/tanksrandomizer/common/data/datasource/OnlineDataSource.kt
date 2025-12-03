package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MasteryTank

interface OnlineDataSource {
    suspend fun logIn(): Result<String, Error>
    suspend fun logOut(currentToken: Token): EmptyResult<Error>
    suspend fun getNewToken(currentToken: Token): Result<Token, Error>
    suspend fun getMasteryTanks(currentToken: Token): Result<List<MasteryTank>, Error>
    suspend fun getEncyclopediaTanks(ids: List<Int>): Result<List<EncyclopediaTank>, Error>
    suspend fun getAllEncyclopediaTanks(): Result<List<EncyclopediaTank>, Error>
}
