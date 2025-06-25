package com.posse.tanksrandomizer.feature_online_pane.data

import com.posse.tanksrandomizer.feature_online_pane.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Token

interface OnlineDataSource {
    fun getToken(): Token?
    fun setToken(token: Token?)

    fun getLastAccountUpdated(): Long?
    fun setLastAccountUpdated(dateTime: Long)

    fun getTanksInGarage(): List<Tank>?
    fun setTanksInGarage(tanks: List<Tank>)

    fun getSelectedTank(): Tank?
    fun setSelectedTank(tank: Tank)
}