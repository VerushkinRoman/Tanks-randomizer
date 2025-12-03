package com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineScreenRepository
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.Numbers
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineFilters
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState

class GetOfflineScreenStartState(
    private val commonTanksRepository: CommonTanksRepository,
    private val offlineScreenRepository: OfflineScreenRepository,
) {
    operator fun invoke(id: String): OfflineScreenState {
        return OfflineScreenState(
            offlineFilters = OfflineFilters(
                tiers = commonTanksRepository.getTiers(id),
                experiences = offlineScreenRepository.getExperiences(id),
                nations = commonTanksRepository.getNations(id),
                pinned = offlineScreenRepository.getPinned(id),
                statuses = offlineScreenRepository.getStatuses(id),
                tankTypes = offlineScreenRepository.getTankTypes(id),
                types = commonTanksRepository.getTypes(id),
            ),
            numbers = Numbers(
                quantity = offlineScreenRepository.getQuantity(id),
            ),
        )
    }
}
