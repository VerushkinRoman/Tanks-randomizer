package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineFilters
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GetOnlineScreenStartState(
    private val commonTanksRepository: CommonTanksRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
    private val filterTanks: FilterTanks,
) {
    operator fun invoke(screenId: String, accountId: Int): OnlineScreenState {
        val tanks = runBlocking {
            onlineScreenRepository.getTanksFlowForAccount(accountId).first()
        }

        val lastAccountUpdated = runBlocking {
            onlineScreenRepository.getLastAccountUpdated(accountId).first()
        }

        val onlineFilters = OnlineFilters(
            tiers = commonTanksRepository.getTiers(screenId),
            nations = commonTanksRepository.getNations(screenId),
            premium = onlineScreenRepository.getPremium(screenId),
            types = commonTanksRepository.getTypes(screenId),
            mastery = onlineScreenRepository.getMastery(screenId)
        )

        val filteredTanks = runBlocking {
            filterTanks(tanks, onlineFilters)
        }

        return OnlineScreenState(
            onlineFilters = onlineFilters,
            tanksInGarage = tanks,
            tanksByFilter = filteredTanks,
            generatedTank = onlineScreenRepository.getSelectedTank(screenId),
            lastAccountUpdated = lastAccountUpdated,
        )
    }
}
