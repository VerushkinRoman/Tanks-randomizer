package com.posse.tanksrandomizer.feature_online_navigation.common.data.models

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import kotlinx.serialization.Serializable

@Serializable
internal data class DataOnlineScreenData(
    val id: String,
    val name: String,
    val position: Int,
    val selected: Boolean,
    val accountId: Int?,
)

internal fun DataOnlineScreenData.toOnlineScreenData(): OnlineScreenData {
    return OnlineScreenData(
        id = id,
        name = name,
        position = position,
        selected = selected,
        accountId = accountId,
    )
}

internal fun OnlineScreenData.toDataOnlineScreenData(): DataOnlineScreenData {
    return DataOnlineScreenData(
        id = id,
        name = name,
        position = position,
        selected = selected,
        accountId = accountId,
    )
}
