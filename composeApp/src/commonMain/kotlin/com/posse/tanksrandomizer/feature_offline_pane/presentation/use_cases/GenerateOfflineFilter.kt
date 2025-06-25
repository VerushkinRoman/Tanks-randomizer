package com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.presentation.use_cases.GenerateRandomItems
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineFilters
import kotlinx.coroutines.withContext

class GenerateOfflineFilter(
    private val dispatchers: Dispatchers,
) {
    private val generateRandomItems = GenerateRandomItems()

    suspend operator fun invoke(offlineFilters: OfflineFilters): OfflineFilters {
        return withContext(dispatchers.io) {
            offlineFilters.copy(
                levels = generateRandomItems(offlineFilters.levels),
                experiences = generateRandomItems(offlineFilters.experiences),
                nations = generateRandomItems(offlineFilters.nations),
                pinned = generateRandomItems(offlineFilters.pinned),
                statuses = generateRandomItems(offlineFilters.statuses),
                tankTypes = generateRandomItems(offlineFilters.tankTypes),
                types = generateRandomItems(offlineFilters.types),
            )
        }
    }
}