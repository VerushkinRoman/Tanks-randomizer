package com.posse.tanksrandomizer.feature_online_screen.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSource
import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_screen.data.datasource.OnlineScreenDataSource
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class OnlineScreenRepositoryImpl(
    private val onlineScreenDataSource: OnlineScreenDataSource,
    private val onlineDataSource: OnlineDataSource,
    private val offlineDataSource: OfflineDataSource,
) : OnlineScreenRepository {
    override fun getMastery(): List<Mastery> = offlineDataSource.getProperties(defaultItems = Mastery.defaultValues)
    override fun setMastery(mastery: List<Mastery>) = offlineDataSource.setProperties(mastery)

    override fun getLastAccountUpdated(): Instant? {
        return onlineScreenDataSource.getLastAccountUpdated()?.let {
            Instant.fromEpochSeconds(it)
        }
    }

    override fun setLastAccountUpdated(dateTime: Instant) {
        onlineScreenDataSource.setLastAccountUpdated(dateTime.epochSeconds)
    }

    override fun getTanksInGarage(): List<Tank> = onlineScreenDataSource.getTanksInGarage()
    override fun setTanksInGarage(tanks: List<Tank>) = onlineScreenDataSource.setTanksInGarage(tanks)

    override fun getSelectedTank(): Tank? = onlineScreenDataSource.getSelectedTank()
    override fun setSelectedTank(tank: Tank?) = onlineScreenDataSource.setSelectedTank(tank)

    override fun getPremium(): List<Premium> = offlineDataSource.getProperties(defaultItems = Premium.defaultValues)

    override fun setPremium(premium: List<Premium>) = offlineDataSource.setProperties(premium)

    override suspend fun getAccountTanks(): Result<List<AccountTank>, Error> {
        val token = offlineDataSource.getToken() ?: return Result.Error(DomainErrorType.TokenError)
        return onlineDataSource.getAccountTanks(currentToken = token)
    }

    override suspend fun getEncyclopediaTanks(ids: List<Int>): Result<List<EncyclopediaTank>, Error> {
        val token = offlineDataSource.getToken() ?: return Result.Error(DomainErrorType.TokenError)
        return onlineDataSource.getEncyclopediaTanks(ids = ids, currentToken = token)
    }
}
