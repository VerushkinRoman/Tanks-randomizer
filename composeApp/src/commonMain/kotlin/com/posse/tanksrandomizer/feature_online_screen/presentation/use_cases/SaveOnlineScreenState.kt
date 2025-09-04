package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenState
import kotlinx.coroutines.withContext

class SaveOnlineScreenState(
    private val filterRepository: CommonTanksRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(state: OnlineScreenState) = withContext(dispatchers.io) {
        filterRepository.setTypes(state.onlineFilters.types)
        onlineScreenRepository.setPremium(state.onlineFilters.premium)
        filterRepository.setTiers(state.onlineFilters.tiers)
        filterRepository.setNations(state.onlineFilters.nations)
        onlineScreenRepository.setMastery(state.onlineFilters.mastery)
        onlineScreenRepository.setSelectedTank(state.generatedTank)
        state.lastAccountUpdated?.let { onlineScreenRepository.setLastAccountUpdated(it) }
    }
}