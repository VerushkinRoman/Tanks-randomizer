package com.posse.tanksrandomizer.common.data.models

import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.EncyclopediaTank
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

fun EncyclopediaTankData.toEncyclopediaTank(): EncyclopediaTank {
    return EncyclopediaTank(
        id = tankId,
        isPremium = isPremium,
        name = name,
        nationName = nation,
        tier = tier,
        typeName = type,
        image = images.preview,
    )
}
