package com.posse.tanksrandomizer.common.paged_screens_navigation.data.models

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.ScreenMetadata
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import kotlinx.serialization.Serializable

@Serializable
internal data class DataOnlineScreenData(
    val id: String,
    val name: String?,
    val position: Int,
    val selected: Boolean,
    val accountId: Int?,
)

internal fun DataOnlineScreenData.toOnlineScreenData(): OnlineScreenData {
    return OnlineScreenData(
        ScreenMetadata(
            id = id,
            name = name,
            position = position,
            selected = selected,
        ),
        additionalData = accountId,
    )
}

internal fun OnlineScreenData.toDataOnlineScreenData(): DataOnlineScreenData {
    return DataOnlineScreenData(
        id = metadata.id,
        name = metadata.name,
        position = metadata.position,
        selected = metadata.selected,
        accountId = accountId,
    )
}
