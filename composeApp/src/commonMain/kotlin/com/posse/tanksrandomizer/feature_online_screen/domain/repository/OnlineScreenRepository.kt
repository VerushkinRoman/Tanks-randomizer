package com.posse.tanksrandomizer.feature_online_screen.domain.repository

import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import kotlinx.datetime.LocalDateTime

interface OnlineScreenRepository {
    fun getMastery(): List<Mastery>
    fun setMastery(mastery: List<Mastery>)

    fun getLastAccountUpdated(): LocalDateTime?
    fun setLastAccountUpdated(dateTime: LocalDateTime)

    fun getTanksInGarage(): List<Tank>?
    fun setTanksInGarage(tanks: List<Tank>)

    fun getSelectedTank(): Tank?
    fun setSelectedTank(tank: Tank?)

    fun getPremium(): List<Premium>
    fun setPremium(premium: List<Premium>)

    suspend fun getAccountTanks(): Result<List<AccountTank>, Error>
    suspend fun getEncyclopediaTanks(ids: List<Int>): Result<List<EncyclopediaTank>, Error>
}
