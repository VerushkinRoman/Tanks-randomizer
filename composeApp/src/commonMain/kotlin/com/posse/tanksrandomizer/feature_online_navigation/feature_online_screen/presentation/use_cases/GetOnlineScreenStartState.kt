package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineFilters
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenState
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GetOnlineScreenStartState(
    private val accountRepository: AccountRepository,
    private val commonTanksRepository: CommonTanksRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
    private val filterTanks: FilterTanks,
) {
    operator fun invoke(): OnlineScreenState {
        val onlineFilters = OnlineFilters(
            tiers = commonTanksRepository.getTiers(),
            nations = commonTanksRepository.getNations(),
            premium = onlineScreenRepository.getPremium(),
            types = commonTanksRepository.getTypes(),
            mastery = onlineScreenRepository.getMastery()
        )

        val tanksInGarage = onlineScreenRepository.getTanksInGarage()

        val tanksByFilter = runBlocking {
            filterTanks(tanks = tanksInGarage, filters = onlineFilters)
        }

        return OnlineScreenState(
            nickname = accountRepository.getNickname(),
            onlineFilters = onlineFilters,
            tanksInGarage = tanksInGarage,
            tanksByFilter = tanksByFilter,
            generatedTank = onlineScreenRepository.getSelectedTank(),
            lastAccountUpdated = onlineScreenRepository.getLastAccountUpdated(),
        )
    }
}
