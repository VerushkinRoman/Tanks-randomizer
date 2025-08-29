package com.posse.tanksrandomizer.feature_online_screen.domain.repository

import com.posse.tanksrandomizer.common.data.OfflineCommonDataSource
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_screen.data.NetworkDataSource
import com.posse.tanksrandomizer.feature_online_screen.data.OnlineDataSource
import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class OnlineRepositoryImpl(
    private val offlineCommonDataSource: OfflineCommonDataSource,
    private val onlineDataSource: OnlineDataSource,
    private val networkDataSource: NetworkDataSource,
) : OnlineRepository {
    override fun getMastery(): List<Mastery> = offlineCommonDataSource.getProperties(Mastery.defaultValues)
    override fun setMastery(mastery: List<Mastery>) = offlineCommonDataSource.setProperties(mastery)

    override fun getLastAccountUpdated(): LocalDateTime? {
        return onlineDataSource.getLastAccountUpdated()?.let {
            Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault())
        }
    }

    override fun setLastAccountUpdated(dateTime: LocalDateTime) {
        onlineDataSource.setLastAccountUpdated(
            dateTime.toInstant(TimeZone.currentSystemDefault()).epochSeconds
        )
    }

    override fun getTanksInGarage(): List<Tank>? = onlineDataSource.getTanksInGarage()
    override fun setTanksInGarage(tanks: List<Tank>) = onlineDataSource.setTanksInGarage(tanks)

    override fun getSelectedTank(): Tank? = onlineDataSource.getSelectedTank()
    override fun setSelectedTank(tank: Tank?) = onlineDataSource.setSelectedTank(tank)

    override fun getPremium(): List<Premium> = offlineCommonDataSource.getProperties(Premium.defaultValues)
    override fun setPremium(premium: List<Premium>) = offlineCommonDataSource.setProperties(premium)

    override suspend fun getAccountTanks(): Result<List<AccountTank>, Error> = networkDataSource.getAccountTanks()
    override suspend fun getEncyclopediaTanks(ids: List<Int>): Result<List<EncyclopediaTank>, Error> = networkDataSource.getEncyclopediaTanks(ids)
}
