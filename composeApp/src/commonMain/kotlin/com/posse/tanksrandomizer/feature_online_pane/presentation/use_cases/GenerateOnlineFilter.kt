package com.posse.tanksrandomizer.feature_online_pane.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.presentation.use_cases.GenerateRandomItems
import com.posse.tanksrandomizer.feature_online_pane.presentation.models.OnlineFilters
import kotlinx.coroutines.withContext

class GenerateOnlineFilter(
    private val dispatchers: Dispatchers,
) {
    private val generateRandomItems = GenerateRandomItems()

    suspend operator fun invoke(onlineFilters: OnlineFilters): OnlineFilters {
        return withContext(dispatchers.io) {
            onlineFilters.copy(
                levels = generateRandomItems(onlineFilters.levels),
                nations = generateRandomItems(onlineFilters.nations),
                tankTypes = generateRandomItems(onlineFilters.tankTypes),
                types = generateRandomItems(onlineFilters.types),
                mastery = generateRandomItems(onlineFilters.mastery)
            )
        }
    }
}