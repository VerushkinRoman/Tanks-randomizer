package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MasteryTank

interface OnlineDataSource {
    suspend fun logIn(): Result<String, Error>
    suspend fun logOut(accountId: Int): EmptyResult<Error>
    suspend fun getMasteryTanks(accountId: Int): Result<List<MasteryTank>, Error>
    suspend fun getEncyclopediaTanks(ids: List<Int>): Result<List<EncyclopediaTank>, Error>
    suspend fun getAllEncyclopediaTanks(): Result<List<EncyclopediaTank>, Error>
}
