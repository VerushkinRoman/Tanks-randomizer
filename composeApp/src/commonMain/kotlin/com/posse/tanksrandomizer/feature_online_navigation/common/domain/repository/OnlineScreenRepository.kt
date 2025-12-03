package com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository

import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.AccountTanks
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MasteryTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

interface OnlineScreenRepository {
    fun getMastery(screenId: String): List<Mastery>
    fun setMastery(screenId: String, mastery: List<Mastery>)

    @OptIn(ExperimentalTime::class)
    fun getLastAccountUpdated(accountId: Int): Flow<Instant?>
    @OptIn(ExperimentalTime::class)
    fun setLastAccountUpdated(accountId: Int, dateTime: Instant?)

    suspend fun refreshTanks(accountId: Int): EmptyResult<Error>

    fun getSelectedTank(screenId: String): Tank?
    fun setSelectedTank(screenId: String, tank: Tank?)

    fun getPremium(screenId: String): List<Premium>
    fun setPremium(screenId: String, premium: List<Premium>)

    fun clearScreenData(screenId: String)
    suspend fun clearAccountData(accountId: Int)

    fun getTanksFlowForAccount(accountId: Int): Flow<List<Tank>>
}
