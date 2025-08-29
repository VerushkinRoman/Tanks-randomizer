package com.posse.tanksrandomizer.feature_online_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class OnlineScreenState(
    val onlineFilters: OnlineFilters,
    val tanksInGarage: List<Tank>,
    val tanksByFilter: List<Tank>,
    val generatedTank: Tank?,
    val numberOfFilteredTanks: Int,
    val token: Token?,
    val lastAccountUpdated: LocalDateTime?,
) {
    @OptIn(ExperimentalTime::class)
    fun updateTanks(
        newTanks: List<Tank>,
        tanksByFilter: List<Tank>,
    ): OnlineScreenState {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        return copy(
            tanksInGarage = newTanks,
            tanksByFilter = tanksByFilter,
            numberOfFilteredTanks = tanksByFilter.size,
            lastAccountUpdated = now
        )
    }

    fun updateFilters(
        newFilters: OnlineFilters,
        tanksByFilter: List<Tank>,
    ): OnlineScreenState {
        return copy(
            onlineFilters = newFilters,
            tanksByFilter = tanksByFilter,
            numberOfFilteredTanks = tanksByFilter.size,
            generatedTank = null
        )
    }
}
