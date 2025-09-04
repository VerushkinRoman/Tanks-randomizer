package com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineScreenRepository
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState
import kotlinx.coroutines.withContext

class SaveOfflineScreenState(
    private val filterRepository: CommonTanksRepository,
    private val offlineScreenRepository: OfflineScreenRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(state: OfflineScreenState) = withContext(dispatchers.io) {
        filterRepository.setTypes(state.offlineFilters.types)
        filterRepository.setTiers(state.offlineFilters.tiers)
        filterRepository.setNations(state.offlineFilters.nations)
        offlineScreenRepository.setTankTypes(state.offlineFilters.tankTypes)
        offlineScreenRepository.setPinned(state.offlineFilters.pinned)
        offlineScreenRepository.setStatuses(state.offlineFilters.statuses)
        offlineScreenRepository.setExperiences(state.offlineFilters.experiences)
        offlineScreenRepository.setQuantity(state.numbers.quantity)
    }
}