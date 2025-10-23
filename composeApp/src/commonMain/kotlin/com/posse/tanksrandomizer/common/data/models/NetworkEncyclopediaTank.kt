package com.posse.tanksrandomizer.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EncyclopediaTankData(
    val name: String,
    val nation: String,
    @SerialName("is_premium") val isPremium: Boolean,
    val tier: Int,
    val images: ImageData,
    val type: String,
    @SerialName("tank_id") val tankId: Int,
)

@Serializable
data class ImageData(
    val preview: String,
)

typealias NetworkEncyclopediaTankData = Map<String, EncyclopediaTankData?>
