package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineFilters
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenState
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GetOnlineScreenStartState(
    private val commonTanksRepository: CommonTanksRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
) {
    operator fun invoke(screenId: String): OnlineScreenState {
        val onlineFilters = OnlineFilters(
            tiers = commonTanksRepository.getTiers(screenId),
            nations = commonTanksRepository.getNations(screenId),
            premium = onlineScreenRepository.getPremium(screenId),
            types = commonTanksRepository.getTypes(screenId),
            mastery = onlineScreenRepository.getMastery(screenId)
        )

        return OnlineScreenState(
            onlineFilters = onlineFilters,
            generatedTank = onlineScreenRepository.getSelectedTank(screenId),
        )
    }
}
