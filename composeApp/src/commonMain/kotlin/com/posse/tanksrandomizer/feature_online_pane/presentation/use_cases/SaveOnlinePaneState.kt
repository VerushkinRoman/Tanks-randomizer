package com.posse.tanksrandomizer.feature_online_pane.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_online_pane.domain.repository.OnlineRepository
import com.posse.tanksrandomizer.feature_online_pane.presentation.models.OnlinePaneState
import kotlinx.coroutines.withContext

class SaveOnlinePaneState(
    private val filterRepository: CommonRepository,
    private val onlineRepository: OnlineRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(state: OnlinePaneState) = withContext(dispatchers.io) {
        filterRepository.setTypes(state.onlineFilters.types)
        filterRepository.setTankTypes(state.onlineFilters.tankTypes)
        filterRepository.setLevels(state.onlineFilters.levels)
        filterRepository.setNations(state.onlineFilters.nations)
        onlineRepository.setMastery(state.onlineFilters.mastery)
        state.token?.let { onlineRepository.setToken(it) }
        state.lastAccountUpdated?.let { onlineRepository.setLastAccountUpdated(it) }
    }
}