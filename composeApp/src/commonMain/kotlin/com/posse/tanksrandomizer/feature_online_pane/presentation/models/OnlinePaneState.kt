package com.posse.tanksrandomizer.feature_online_pane.presentation.models

import com.posse.tanksrandomizer.common.domain.utils.BoxedInt
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Token
import kotlinx.datetime.LocalDateTime

data class OnlinePaneState(
    val onlineFilters: OnlineFilters,
    val tanksInGarage: List<Tank>,
    val tanksByFilter: List<Tank>,
    val selectedTank: Tank?,
    val numberOfFilteredTanks: BoxedInt,
    val token: Token?,
    val lastAccountUpdated: LocalDateTime?,
)
