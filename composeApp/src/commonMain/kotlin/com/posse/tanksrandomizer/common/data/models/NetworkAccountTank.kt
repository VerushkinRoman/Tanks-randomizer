package com.posse.tanksrandomizer.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAccountTank(
    val status: String,
    val meta: AccountMetaData,
    val data: Map<String, List<AccountTankData>>,
)

@Serializable
data class AccountMetaData(
    val count: Int,
)

@Serializable
data class AccountTankData(
    @SerialName("mark_of_mastery") val markOfMastery: Int,
    @SerialName("tank_id") val tankId: Int,
)
