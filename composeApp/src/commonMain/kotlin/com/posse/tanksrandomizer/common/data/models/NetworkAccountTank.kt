package com.posse.tanksrandomizer.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAccountTank(
    @SerialName("mark_of_mastery") val markOfMastery: Int,
    @SerialName("tank_id") val tankId: Int,
)

typealias NetworkAccountTankResponse = Map<String, List<NetworkAccountTank>>
