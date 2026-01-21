package com.posse.tanksrandomizer.common.presentation.ads.models

import android.content.Context

sealed interface RewardAdEvent {
    class OnScreenStart(val context: Context) : RewardAdEvent
    class ShowRewardedAd(val context: Context) : RewardAdEvent
}
