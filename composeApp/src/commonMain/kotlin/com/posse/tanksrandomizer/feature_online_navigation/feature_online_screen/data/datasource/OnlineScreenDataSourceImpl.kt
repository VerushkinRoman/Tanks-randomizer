package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.datasource

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MasteryTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.DBEncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.DBMasteryTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.toDBEncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.toDBMasteryTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.toEncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.toMasteryTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getLongOrNullFlow
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.set
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class OnlineScreenDataSourceImpl(
    private val settings: Settings,
    private val observableSettings: ObservableSettings,
    private val database: Realm,
) : OnlineScreenDataSource {
    override fun getLastAccountUpdated(accountId: Int): Flow<Long?> =
        observableSettings.getLongOrNullFlow("${LAST_ACCOUNT_UPDATED_KEY}_$accountId")

    override fun setLastAccountUpdated(accountId: Int, dateTime: Long?) =
        observableSettings.set("${LAST_ACCOUNT_UPDATED_KEY}_$accountId", value = dateTime)

    override fun getMasteryTanks(accountId: Int): Flow<List<MasteryTank>> {
        return database
            .query<DBMasteryTank>("accountId == $0", accountId)
            .find()
            .asFlow()
            .distinctUntilChanged()
            .map { resultsChange ->
                resultsChange
                    .list
                    .map { it.toMasteryTank() }
            }
    }

    override suspend fun setMasteryTanks(tanks: List<MasteryTank>) {
        if (tanks.isEmpty()) return
        database.write {
            tanks.forEach { tank ->
                val existingTank = query<DBMasteryTank>(
                    query = "accountId == $0 AND tankId == $1",
                    tank.accountId,
                    tank.tankId
                )
                    .first()
                    .find()

                if (existingTank != null) {
                    existingTank.mastery = tank.mastery
                } else {
                    copyToRealm(
                        instance = tank.toDBMasteryTank(),
                        updatePolicy = UpdatePolicy.ALL
                    )
                }
            }
        }
    }

    override suspend fun deleteMasteryTanks(accountId: Int) {
        database.write {
            query<DBMasteryTank>("accountId == $0", accountId)
                .find()
                .let { delete(it) }
        }
    }

    override fun getAllMasteryTanks(): Flow<List<MasteryTank>> {
        return database
            .query<DBMasteryTank>()
            .find()
            .asFlow()
            .distinctUntilChanged()
            .map { resultsChange ->
                resultsChange
                    .list
                    .map { it.toMasteryTank() }
            }
    }

    override fun getSelectedTank(screenId: String): Tank? =
        settings.decodeValueOrNull("${SELECTED_TANK_KEY}_$screenId")

    override fun setSelectedTank(screenId: String, tank: Tank?) =
        settings.encodeValue("${SELECTED_TANK_KEY}_$screenId", value = tank)

    override fun getEncyclopediaTanks(): Flow<List<EncyclopediaTank>> {
        return database
            .query<DBEncyclopediaTank>()
            .find()
            .asFlow()
            .distinctUntilChanged()
            .map { resultsChange ->
                resultsChange
                    .list
                    .map { it.toEncyclopediaTank() }
            }
    }

    override suspend fun setEncyclopediaTanks(tanks: List<EncyclopediaTank>) {
        if (tanks.isEmpty()) return
        database.write {
            tanks.forEach {
                copyToRealm(it.toDBEncyclopediaTank(), updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    override fun getLastEncyclopediaAllTanksUpdated(): Long? =
        settings.getLongOrNull(LAST_TANKS_UPDATED_KEY)

    override fun setLastEncyclopediaAllTanksUpdated(dateTime: Long) =
        settings.set(LAST_TANKS_UPDATED_KEY, value = dateTime)

    companion object {
        private const val LAST_ACCOUNT_UPDATED_KEY = "LastAccountUpdated"
        private const val LAST_TANKS_UPDATED_KEY = "LastTanksUpdated"
        private const val SELECTED_TANK_KEY = "SelectedTank"
    }
}
