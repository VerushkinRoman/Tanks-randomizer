package com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.FilterRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_offline_pane.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineScreenState
import kotlinx.coroutines.withContext

class SaveOfflineScreenState(
    private val filterRepository: FilterRepository,
    private val offlineRepository: OfflineRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(state: OfflineScreenState) = withContext(dispatchers.io) {
        filterRepository.setPinned(state.filters.pinned)
        filterRepository.setTypes(state.filters.types)
        filterRepository.setStatuses(state.filters.statuses)
        filterRepository.setTankTypes(state.filters.tankTypes)
        filterRepository.setExperiences(state.filters.experiences)
        filterRepository.setLevels(state.filters.levels)
        filterRepository.setNations(state.filters.nations)
        offlineRepository.setQuantity(state.numbers.quantity)
    }
}