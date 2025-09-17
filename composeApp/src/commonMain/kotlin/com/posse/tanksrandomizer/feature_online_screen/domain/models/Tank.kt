package com.posse.tanksrandomizer.feature_online_screen.domain.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import kotlinx.serialization.Serializable

@Serializable
data class Tank(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val tier: Tier,
    val nation: Nation,
    val isPremium: Boolean,
    val type: Type,
    val mastery: Mastery,
)
