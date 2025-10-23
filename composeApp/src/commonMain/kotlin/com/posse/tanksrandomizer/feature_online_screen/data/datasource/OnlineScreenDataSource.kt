package com.posse.tanksrandomizer.feature_online_screen.data.datasource

import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank

interface OnlineScreenDataSource {
    fun getLastAccountUpdated(): Long?
    fun setLastAccountUpdated(dateTime: Long)

    fun getTanksInGarage(): List<Tank>
    fun setTanksInGarage(tanks: List<Tank>)

    fun getSelectedTank(): Tank?
    fun setSelectedTank(tank: Tank?)
}
