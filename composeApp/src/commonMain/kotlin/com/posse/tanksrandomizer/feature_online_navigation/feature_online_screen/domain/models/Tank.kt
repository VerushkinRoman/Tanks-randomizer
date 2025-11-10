package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Tank(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val tier: Int,
    val nationName: String,
    val isPremium: Boolean,
    val typeName: String,
    val mastery: Int,
)
