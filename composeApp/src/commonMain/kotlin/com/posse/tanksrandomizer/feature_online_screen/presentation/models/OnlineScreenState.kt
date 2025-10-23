package com.posse.tanksrandomizer.feature_online_screen.presentation.models

import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class OnlineScreenState(
    val onlineFilters: OnlineFilters,
    val tanksInGarage: List<Tank>,
    val tanksByFilter: List<Tank>,
    val generatedTank: Tank?,
    val lastAccountUpdated: Instant?,
    val loading: Boolean = false,
) {
    fun updateTanks(
        newTanks: List<Tank>,
        tanksByFilter: List<Tank>,
    ): OnlineScreenState {
        return copy(
            tanksInGarage = newTanks,
            tanksByFilter = tanksByFilter,
            lastAccountUpdated = Clock.System.now()
        )
    }

    fun updateFilters(
        newFilters: OnlineFilters,
        tanksByFilter: List<Tank>,
    ): OnlineScreenState {
        return copy(
            onlineFilters = newFilters,
            tanksByFilter = tanksByFilter,
            generatedTank = null
        )
    }
}
