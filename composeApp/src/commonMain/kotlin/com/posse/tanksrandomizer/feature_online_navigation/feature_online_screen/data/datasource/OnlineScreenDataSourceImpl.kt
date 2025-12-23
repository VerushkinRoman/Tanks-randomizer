package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.datasource

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MasteryTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.AppDatabase
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models.toDBEncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models.toDBMasteryTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models.toEncyclopediaTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models.toMasteryTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getLongOrNullFlow
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class OnlineScreenDataSourceImpl(
    private val settings: Settings,
    private val observableSettings: ObservableSettings,
    private val database: AppDatabase,
) : OnlineScreenDataSource {
    override fun getLastAccountUpdated(accountId: Int): Flow<Long?> =
        observableSettings.getLongOrNullFlow("${LAST_ACCOUNT_UPDATED_KEY}_$accountId")

    override fun setLastAccountUpdated(accountId: Int, dateTime: Long?) =
        observableSettings.set("${LAST_ACCOUNT_UPDATED_KEY}_$accountId", value = dateTime)

    override suspend fun setMasteryTanks(tanks: List<MasteryTank>) {
        if (tanks.isEmpty()) return

        database.getMasteryTanksDao().insertOrReplaceAll(
            tanks.map { it.toDBMasteryTank() }
        )
    }

    override suspend fun deleteMasteryTanks(accountId: Int) {
        database.getMasteryTanksDao().deleteByAccountId(accountId)
    }

    override fun getAllMasteryTanks(): Flow<List<MasteryTank>> {
        return database
            .getMasteryTanksDao()
            .getAllAsFlow()
            .distinctUntilChanged()
            .map { dBMasteryTanks ->
                dBMasteryTanks.map { it.toMasteryTank() }
            }
    }

    override fun getMasteryTanks(accountId: Int): Flow<List<MasteryTank>> {
        return database
            .getMasteryTanksDao()
            .getAllByAccountIdAsFlow(accountId)
            .distinctUntilChanged()
            .map { dBMasteryTanks ->
                dBMasteryTanks.map { it.toMasteryTank() }
            }
    }

    override fun getSelectedTank(screenId: String): Tank? =
        settings.decodeValueOrNull("${SELECTED_TANK_KEY}_$screenId")

    override fun setSelectedTank(screenId: String, tank: Tank?) =
        settings.encodeValue("${SELECTED_TANK_KEY}_$screenId", value = tank)

    override fun getEncyclopediaTanks(): Flow<List<EncyclopediaTank>> {
        return database
            .getEncyclopediaTanksDao()
            .getAllAsFlow()
            .distinctUntilChanged()
            .map { dbTanks ->
                dbTanks.map { it.toEncyclopediaTank() }
            }
    }

    override suspend fun setEncyclopediaTanks(tanks: List<EncyclopediaTank>) {
        if (tanks.isEmpty()) return

        database.getEncyclopediaTanksDao().insertOrReplaceAll(
            tanks.map { it.toDBEncyclopediaTank() }
        )
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
