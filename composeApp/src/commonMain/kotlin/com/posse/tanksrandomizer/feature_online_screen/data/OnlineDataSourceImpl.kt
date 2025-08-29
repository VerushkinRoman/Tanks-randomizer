package com.posse.tanksrandomizer.feature_online_screen.data

import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.set
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class OnlineDataSourceImpl(
    private val settings: Settings,
) : OnlineDataSource {
    override fun getLastAccountUpdated(): Long? = settings.getLongOrNull(key = "LastAccountUpdated")
    override fun setLastAccountUpdated(dateTime: Long) = settings.set(key = "LastAccountUpdated", value = dateTime)

    override fun getTanksInGarage(): List<Tank>? = settings.decodeValueOrNull(key = "TanksInGarage")
    override fun setTanksInGarage(tanks: List<Tank>) = settings.encodeValue(key = "TanksInGarage", value = tanks)

    override fun getSelectedTank(): Tank? = settings.decodeValueOrNull(key = "SelectedTank")
    override fun setSelectedTank(tank: Tank?) = settings.encodeValue(key = "SelectedTank", value = tank)
}
