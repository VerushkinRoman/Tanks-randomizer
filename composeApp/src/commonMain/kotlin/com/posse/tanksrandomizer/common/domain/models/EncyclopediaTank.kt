package com.posse.tanksrandomizer.common.domain.models

import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank

data class EncyclopediaTank(
    val id: Int,
    val isPremium: Boolean,
    val name: String,
    val nationName: String,
    val tier: Int,
    val typeName: String,
    val image: String,
)

fun EncyclopediaTank.toTank(mastery: Int): Tank {
    return Tank(
        id = id,
        name = name,
        imageUrl = image,
        tier = tier,
        nationName = nationName,
        isPremium = isPremium,
        typeName = typeName,
        mastery = mastery
    )
}
