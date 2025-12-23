package com.posse.tanksrandomizer.feature_online_navigation.common.domain.models

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.ScreenMetadata

data class OnlineScreenData(
    override val metadata: ScreenMetadata,
    override val additionalData: Int?,
) : PagedScreen<Int?> {
    val accountId: Int? get() = additionalData
    override fun withMetadata(metadata: ScreenMetadata) = copy(metadata = metadata)
}
