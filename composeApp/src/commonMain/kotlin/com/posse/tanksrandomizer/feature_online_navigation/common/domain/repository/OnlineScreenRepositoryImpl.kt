package com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OnlineDataSource
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.EmptyResult
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.AccountTanks
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MissedEncyclopediaTanks
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.toTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.datasource.OnlineScreenDataSource
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class OnlineScreenRepositoryImpl(
    private val onlineScreenDataSource: OnlineScreenDataSource,
    private val onlineDataSource: OnlineDataSource,
    private val offlineDataSource: OfflineDataSource,
    dispatchers: Dispatchers,
) : OnlineScreenRepository {
    private val coroutineScope = CoroutineScope(dispatchers.io + SupervisorJob())
    private val accountTanks = MutableStateFlow<List<AccountTanks>>(emptyList())

    init {
        coroutineScope.launch {
            onlineScreenDataSource.getEncyclopediaTanks()
                .combine(onlineScreenDataSource.getAllMasteryTanks()) { encyclopediaTanks, masteryTanks ->
                    val groupedByAccount = masteryTanks.groupBy { it.accountId }
                    val resultList: List<AccountTanks> =
                        groupedByAccount.map { (accountId, tanks) ->
                            val accountTanksList = encyclopediaTanks
                                .mapNotNull { encyclopediaTank ->
                                    tanks
                                        .find { tank -> tank.tankId == encyclopediaTank.id }
                                        ?.let { masteryTank ->
                                            encyclopediaTank.toTank(mastery = masteryTank.mastery)
                                        }
                                }

                            AccountTanks(
                                accountId = accountId,
                                tanks = accountTanksList
                            )
                        }

                    resultList
                }.collect { combinedResultList ->
                    accountTanks.value = combinedResultList
                }
        }
    }

    override fun getMastery(screenId: String): List<Mastery> =
        offlineDataSource.getProperties(screenId, Mastery.defaultValues)

    override fun setMastery(screenId: String, mastery: List<Mastery>) =
        offlineDataSource.setProperties(screenId, mastery)

    override fun getLastAccountUpdated(accountId: Int): Flow<Instant?> {
        return onlineScreenDataSource
            .getLastAccountUpdated(accountId)
            .map { value ->
                value?.let {
                    Instant.fromEpochSeconds(it)
                }
            }
    }

    override fun setLastAccountUpdated(accountId: Int, dateTime: Instant?) {
        onlineScreenDataSource.setLastAccountUpdated(accountId, dateTime?.epochSeconds)
    }

    override suspend fun refreshTanks(accountId: Int): EmptyResult<Error> {
        val lastEncyclopediaTanksUpdated =
            onlineScreenDataSource.getLastEncyclopediaAllTanksUpdated()
        val encyclopediaTanks = if (lastEncyclopediaTanksUpdated != null
            && Instant.fromEpochSeconds(lastEncyclopediaTanksUpdated) + 30.days > Clock.System.now()
        ) {
            onlineScreenDataSource.getEncyclopediaTanks().first()
        } else {
            when (val result = onlineDataSource.getAllEncyclopediaTanks()) {
                is Result.Error -> return result
                is Result.Success -> result.data.also {
                    onlineScreenDataSource.setEncyclopediaTanks(it)
                    onlineScreenDataSource.setLastEncyclopediaAllTanksUpdated(Clock.System.now().epochSeconds)
                }
            }
        }

        return updateTanks(encyclopediaTanks, accountId)
    }

    override fun getSelectedTank(screenId: String) =
        onlineScreenDataSource.getSelectedTank(screenId)

    override fun setSelectedTank(screenId: String, tank: Tank?) =
        onlineScreenDataSource.setSelectedTank(screenId, tank)

    override fun getPremium(screenId: String) =
        offlineDataSource.getProperties(screenId, Premium.defaultValues)

    override fun setPremium(screenId: String, premium: List<Premium>) =
        offlineDataSource.setProperties(screenId, premium)

    override fun clearScreenData(screenId: String) {
        setSelectedTank(screenId, null)
        setMastery(screenId, emptyList())
        setPremium(screenId, emptyList())
    }

    override suspend fun clearAccountData(accountId: Int) {
        setLastAccountUpdated(accountId, null)
        onlineScreenDataSource.deleteMasteryTanks(accountId)
    }

    override fun getTanksFlowForAccount(accountId: Int): Flow<List<Tank>> {
        return accountTanks
            .map { accountsList ->
                accountsList.find { it.accountId == accountId }?.tanks ?: emptyList()
            }
    }

    private suspend fun updateTanks(
        encyclopediaTanks: List<EncyclopediaTank>,
        accountId: Int,
    ): EmptyResult<Error> = withContext(currentCoroutineContext()) {
        val masteryTanks = onlineDataSource
            .getMasteryTanks(accountId)
            .let { result ->
                when (result) {
                    is Result.Error -> return@withContext result
                    is Result.Success -> result.data
                        .also { masteryTanks ->
                            launch { onlineScreenDataSource.setMasteryTanks(masteryTanks) }
                        }
                }
            }

        val accountTanks = masteryTanks.map { it.tankId }.toSet()
        val encyclopediaTanksIds = encyclopediaTanks.map { it.id }.toSet()

        val missedTanksInEncyclopedia = accountTanks
            .filter { it !in encyclopediaTanksIds }
            .also { println(it) }

        val missedOnlineEncyclopediaTanks = when (val result = onlineDataSource
            .getEncyclopediaTanks(missedTanksInEncyclopedia)) {
            is Result.Error -> return@withContext result
            is Result.Success -> result.data
        }

        val missedOnlineEncyclopediaTanksIds = missedOnlineEncyclopediaTanks.map { it.id }.toSet()
        val missedIds = accountTanks - encyclopediaTanksIds - missedOnlineEncyclopediaTanksIds
        val missedTanks = MissedEncyclopediaTanks.value.filter { it.id in missedIds }

        launch {
            onlineScreenDataSource.setEncyclopediaTanks(missedTanks + missedOnlineEncyclopediaTanks)
        }

        launch {
            onlineScreenDataSource.setLastAccountUpdated(
                accountId = accountId,
                dateTime = Clock.System.now().epochSeconds
            )
        }

        Result.Success(Unit)
    }
}
