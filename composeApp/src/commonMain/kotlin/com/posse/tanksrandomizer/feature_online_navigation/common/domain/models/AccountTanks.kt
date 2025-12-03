package com.posse.tanksrandomizer.feature_online_navigation.common.domain.models

import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank

data class AccountTanks(
    val accountId: Int,
    val tanks: List<Tank>,
)
