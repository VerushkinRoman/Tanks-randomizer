package com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.presentation.use_cases.GenerateRandomItems
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineFilters
import kotlinx.coroutines.withContext
import kotlin.random.Random

class GenerateOfflineFilter(
    private val dispatchers: Dispatchers,
) {
    private val generateRandomItems = GenerateRandomItems()

    suspend operator fun invoke(
        offlineFilters: OfflineFilters,
        previousFilters: OfflineFilters,
    ): OfflineFilters = withContext(dispatchers.io) {
        when {
            offlineFilters == previousFilters -> previousFilters
            else -> generateUniqueFilters(offlineFilters, previousFilters)
        }
    }

    private fun generateUniqueFilters(
        offlineFilters: OfflineFilters,
        previousFilters: OfflineFilters,
    ): OfflineFilters {
        return generateSequence { offlineFilters.generateRandom() }
            .first { isFiltersAllowed(offlineFilters = it, previousFilters = previousFilters) }
    }

    private fun isFiltersAllowed(
        offlineFilters: OfflineFilters,
        previousFilters: OfflineFilters,
    ): Boolean {
        val allowDuplicate = Random.nextFloat() < 0.3
        val filtersAreDifferent = offlineFilters != previousFilters
        return filtersAreDifferent || allowDuplicate
    }

    private fun OfflineFilters.generateRandom() = copy(
        tiers = generateRandomItems(tiers),
        experiences = generateRandomItems(experiences),
        nations = generateRandomItems(nations),
        pinned = generateRandomItems(pinned),
        statuses = generateRandomItems(statuses),
        tankTypes = generateRandomItems(tankTypes),
        types = generateRandomItems(types),
    )
}
