package com.posse.tanksrandomizer.common.presentation.ads.models

import com.posse.tanksrandomizer.common.presentation.ads.RewardAdViewModel.Companion.MAX_ADS_PER_DAY

data class RewardAdState(
    val adLoaded: Boolean = false,
    val rewardCount: Int = MAX_ADS_PER_DAY,
    val adAvailable: Boolean = false,
)
