package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineFilters
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenState
import kotlinx.coroutines.runBlocking

class GetOnlineScreenStartState(
    private val commonTanksRepository: CommonTanksRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
    private val accountRepository: AccountRepository,
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

        val tanksInGarage = onlineScreenRepository.getTanksInGarage().orEmpty()

        val tanksByFilter = runBlocking {
            filterTanks(tanks = tanksInGarage, filters = onlineFilters)
        }

        return OnlineScreenState(
            onlineFilters = onlineFilters,
            tanksInGarage = tanksInGarage,
            tanksByFilter = tanksByFilter,
            generatedTank = onlineScreenRepository.getSelectedTank(),
            numberOfFilteredTanks = tanksByFilter.size,
            token = accountRepository.getToken(),
            lastAccountUpdated = onlineScreenRepository.getLastAccountUpdated(),
        )
    }
}
