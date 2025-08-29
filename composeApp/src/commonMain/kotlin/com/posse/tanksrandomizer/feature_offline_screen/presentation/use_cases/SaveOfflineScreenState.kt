package com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState
import kotlinx.coroutines.withContext

class SaveOfflineScreenState(
    private val filterRepository: CommonTanksRepository,
    private val offlineRepository: OfflineRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(state: OfflineScreenState) = withContext(dispatchers.io) {
        filterRepository.setTypes(state.offlineFilters.types)
        filterRepository.setLevels(state.offlineFilters.tiers)
        filterRepository.setNations(state.offlineFilters.nations)
        offlineRepository.setTankTypes(state.offlineFilters.tankTypes)
        offlineRepository.setPinned(state.offlineFilters.pinned)
        offlineRepository.setStatuses(state.offlineFilters.statuses)
        offlineRepository.setExperiences(state.offlineFilters.experiences)
        offlineRepository.setQuantity(state.numbers.quantity)
    }
}