package com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineScreenRepository
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState
import kotlinx.coroutines.withContext

class SaveOfflineScreenState(
    private val offlineScreenCommonRepository: CommonTanksRepository,
    private val offlineScreenRepository: OfflineScreenRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(id: String, state: OfflineScreenState) = withContext(dispatchers.io) {
        offlineScreenCommonRepository.setTypes(id, state.offlineFilters.types)
        offlineScreenCommonRepository.setTiers(id, state.offlineFilters.tiers)
        offlineScreenCommonRepository.setNations(id, state.offlineFilters.nations)
        offlineScreenRepository.setTankTypes(id, state.offlineFilters.tankTypes)
        offlineScreenRepository.setPinned(id, state.offlineFilters.pinned)
        offlineScreenRepository.setStatuses(id, state.offlineFilters.statuses)
        offlineScreenRepository.setExperiences(id, state.offlineFilters.experiences)
        offlineScreenRepository.setQuantity(id, state.numbers.quantity)
    }
}
