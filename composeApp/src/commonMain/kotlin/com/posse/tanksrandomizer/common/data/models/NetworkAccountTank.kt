package com.posse.tanksrandomizer.common.data.models

import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.AccountTank
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAccountTank(
    @SerialName("mark_of_mastery") val markOfMastery: Int,
    @SerialName("tank_id") val tankId: Int,
)

typealias NetworkAccountTankResponse = Map<String, List<NetworkAccountTank>>

fun NetworkAccountTank.toAccountTank(): AccountTank {
    return AccountTank(
        id = tankId,
        mastery = markOfMastery + 1
    )
}
