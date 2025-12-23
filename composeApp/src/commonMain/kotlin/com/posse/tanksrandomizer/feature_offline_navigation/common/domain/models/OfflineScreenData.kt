package com.posse.tanksrandomizer.feature_offline_navigation.common.domain.models

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.ScreenMetadata

data class OfflineScreenData(
    override val metadata: ScreenMetadata,
    override val additionalData: Unit = Unit,
) : PagedScreen<Unit> {
    override fun withMetadata(metadata: ScreenMetadata) = copy(metadata = metadata)
}
