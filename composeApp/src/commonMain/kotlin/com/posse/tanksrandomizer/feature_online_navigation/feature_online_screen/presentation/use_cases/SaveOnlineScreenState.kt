package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenState
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class SaveOnlineScreenState(
    private val filterRepository: CommonTanksRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(screenId: String, state: OnlineScreenState) = withContext(dispatchers.io) {
        filterRepository.setTypes(screenId, state.onlineFilters.types)
        onlineScreenRepository.setPremium(screenId, state.onlineFilters.premium)
        filterRepository.setTiers(screenId, state.onlineFilters.tiers)
        filterRepository.setNations(screenId, state.onlineFilters.nations)
        onlineScreenRepository.setMastery(screenId, state.onlineFilters.mastery)
        onlineScreenRepository.setSelectedTank(screenId, state.generatedTank)
    }
}
