package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.datasource

import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.DBTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.toDBTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models.toTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.set
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class OnlineScreenDataSourceImpl(
    private val settings: Settings,
    private val database: Realm,
) : OnlineScreenDataSource {
    override fun getLastAccountUpdated(): Long? = settings.getLongOrNull(key = "LastAccountUpdated")
    override fun setLastAccountUpdated(dateTime: Long?) = settings.set(key = "LastAccountUpdated", value = dateTime)

    override fun getTanksInGarage(): List<Tank> {
        return database
            .query<DBTank>()
            .find()
            .map { it.toTank() }
    }

    override fun setTanksInGarage(tanks: List<Tank>) {
        database.writeBlocking {
            deleteAll()

            tanks.forEach {
                copyToRealm(
                    instance = it.toDBTank(),
                    updatePolicy = UpdatePolicy.ALL
                )
            }
        }
    }

    override fun getSelectedTank(): Tank? = settings.decodeValueOrNull(key = "SelectedTank")
    override fun setSelectedTank(tank: Tank?) = settings.encodeValue(key = "SelectedTank", value = tank)
}
