package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineRepository
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenState
import kotlinx.coroutines.withContext

class SaveOnlineScreenState(
    private val filterRepository: CommonTanksRepository,
    private val onlineRepository: OnlineRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(state: OnlineScreenState) = withContext(dispatchers.io) {
        filterRepository.setTypes(state.onlineFilters.types)
        onlineRepository.setPremium(state.onlineFilters.premium)
        filterRepository.setLevels(state.onlineFilters.tiers)
        filterRepository.setNations(state.onlineFilters.nations)
        onlineRepository.setMastery(state.onlineFilters.mastery)
        onlineRepository.setSelectedTank(state.generatedTank)
        state.lastAccountUpdated?.let { onlineRepository.setLastAccountUpdated(it) }
    }
}