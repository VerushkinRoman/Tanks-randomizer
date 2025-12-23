package com.posse.tanksrandomizer.feature_offline_navigation.common.data.models

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.ScreenMetadata
import com.posse.tanksrandomizer.feature_offline_navigation.common.domain.models.OfflineScreenData
import kotlinx.serialization.Serializable

@Serializable
internal data class DataOfflineScreenData(
    val id: String,
    val name: String?,
    val position: Int,
    val selected: Boolean,
)

internal fun DataOfflineScreenData.toOfflineScreenData(): OfflineScreenData {
    return OfflineScreenData(
        ScreenMetadata(
            id = id,
            name = name,
            position = position,
            selected = selected,
        ),
    )
}

internal fun OfflineScreenData.toDataOfflineScreenData(): DataOfflineScreenData {
    return DataOfflineScreenData(
        id = metadata.id,
        name = metadata.name,
        position = metadata.position,
        selected = metadata.selected,
    )
}
