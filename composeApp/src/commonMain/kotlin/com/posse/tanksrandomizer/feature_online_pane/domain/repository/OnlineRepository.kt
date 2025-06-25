package com.posse.tanksrandomizer.feature_online_pane.domain.repository

import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_pane.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Token
import kotlinx.datetime.LocalDateTime

interface OnlineRepository {
    fun getMastery(): List<Mastery>
    fun setMastery(mastery: List<Mastery>)

    fun getToken(): Token?
    fun setToken(token: Token?)

    fun getLastAccountUpdated(): LocalDateTime?
    fun setLastAccountUpdated(dateTime: LocalDateTime)

    fun getTanksInGarage(): List<Tank>?
    fun setTanksInGarage(tanks: List<Tank>)

    fun getSelectedTank(): Tank?
    fun setSelectedTank(tank: Tank)

    suspend fun logIn(): EmptyResult<NetworkError>
    suspend fun logOut(accessToken: String): EmptyResult<NetworkError>
    suspend fun getNewToken(accessToken: String): Result<Token, NetworkError>
    suspend fun getAccountTanks(): Result<List<Tank>, NetworkError>
}
