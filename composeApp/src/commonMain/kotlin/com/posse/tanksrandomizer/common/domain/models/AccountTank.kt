package com.posse.tanksrandomizer.common.domain.models

import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Mastery

data class AccountTank(
    val id: Int,
    val mastery: Mastery,
)
