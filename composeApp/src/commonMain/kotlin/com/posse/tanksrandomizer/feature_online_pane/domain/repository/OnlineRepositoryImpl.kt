package com.posse.tanksrandomizer.feature_online_pane.domain.repository

import com.posse.tanksrandomizer.common.data.DataSource
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_pane.data.NetworkDataSource
import com.posse.tanksrandomizer.feature_online_pane.data.OnlineDataSource
import com.posse.tanksrandomizer.feature_online_pane.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_pane.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Token
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class OnlineRepositoryImpl(
    private val dataSource: DataSource,
    private val onlineDataSource: OnlineDataSource,
    private val networkDataSource: NetworkDataSource,
) : OnlineRepository {
    override fun getMastery(): List<Mastery> = dataSource.getProperties(Mastery.defaultValues)
    override fun setMastery(mastery: List<Mastery>) = dataSource.setProperties(mastery)

    override fun getToken(): Token? = onlineDataSource.getToken()
    override fun setToken(token: Token?) = onlineDataSource.setToken(token)

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
    override fun setSelectedTank(tank: Tank) = onlineDataSource.setSelectedTank(tank)

    override fun getPremium(): List<Premium> = dataSource.getProperties(Premium.defaultValues)
    override fun setPremium(premium: List<Premium>) = dataSource.setProperties(premium)

    override suspend fun logIn(): EmptyResult<NetworkError> = networkDataSource.logIn()
    override suspend fun logOut(accessToken: String): EmptyResult<NetworkError> = networkDataSource.logOut(accessToken)
    override suspend fun getNewToken(accessToken: String): Result<Token, NetworkError> = networkDataSource.getNewToken(accessToken)
    override suspend fun getAccountTanks(): Result<List<Tank>, NetworkError> = networkDataSource.getTanks()
}