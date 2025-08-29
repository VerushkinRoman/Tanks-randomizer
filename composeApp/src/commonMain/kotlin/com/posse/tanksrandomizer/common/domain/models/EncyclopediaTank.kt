package com.posse.tanksrandomizer.common.domain.models

data class EncyclopediaTank(
    val id: Int,
    val isPremium: Boolean,
    val name: String,
    val nation: CommonFilterObjects.Nation,
    val tier: CommonFilterObjects.Tier,
    val type: CommonFilterObjects.Type,
    val image: String,
)
