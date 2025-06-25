package com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_offline_pane.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneState
import kotlinx.coroutines.withContext

class SaveOfflinePaneState(
    private val filterRepository: CommonRepository,
    private val offlineRepository: OfflineRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(state: OfflinePaneState) = withContext(dispatchers.io) {
        filterRepository.setTypes(state.offlineFilters.types)
        filterRepository.setTankTypes(state.offlineFilters.tankTypes)
        filterRepository.setLevels(state.offlineFilters.levels)
        filterRepository.setNations(state.offlineFilters.nations)
        offlineRepository.setPinned(state.offlineFilters.pinned)
        offlineRepository.setStatuses(state.offlineFilters.statuses)
        offlineRepository.setExperiences(state.offlineFilters.experiences)
        offlineRepository.setQuantity(state.numbers.quantity)
    }
}