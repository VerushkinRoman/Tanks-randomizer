package com.posse.tanksrandomizer.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEncyclopediaTank(
    val status: String,
    val meta: EncyclopediaMetaData,
    val data: Map<String, EncyclopediaTankData>,
)

@Serializable
data class EncyclopediaMetaData(
    val count: Int,
)

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
