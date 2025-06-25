package com.posse.tanksrandomizer.feature_online_pane.domain.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import  com.posse.tanksrandomizer.feature_online_pane.domain.models.OnlineFilterObjects.Mastery
import kotlinx.serialization.Serializable

@Serializable
data class Tank(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val level: Level,
    val nation: Nation,
    val tankType: TankType,
    val type: Type,
    val mastery: Mastery,
)
