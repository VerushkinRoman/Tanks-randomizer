package com.posse.tanksrandomizer.common.data.models

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MasteryTank
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAccountTank(
    @SerialName("mark_of_mastery") val markOfMastery: Int,
    @SerialName("tank_id") val tankId: Int,
)

typealias NetworkAccountTankResponse = Map<String, List<NetworkAccountTank>>

fun NetworkAccountTank.toAccountTank(accountId: Int): MasteryTank {
    return MasteryTank(
        accountId = accountId,
        tankId = tankId,
        mastery = markOfMastery + 1
    )
}
