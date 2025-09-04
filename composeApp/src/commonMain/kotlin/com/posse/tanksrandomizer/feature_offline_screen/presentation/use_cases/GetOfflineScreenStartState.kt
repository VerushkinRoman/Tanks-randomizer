package com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineScreenRepository
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineFilters
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.Numbers

class GetOfflineScreenStartState(
    private val commonTanksRepository: CommonTanksRepository,
    private val offlineScreenRepository: OfflineScreenRepository,
) {
    operator fun invoke(): OfflineScreenState {
        return OfflineScreenState(
            offlineFilters = OfflineFilters(
                tiers = commonTanksRepository.getTiers(),
                experiences = offlineScreenRepository.getExperiences(),
                nations = commonTanksRepository.getNations(),
                pinned = offlineScreenRepository.getPinned(),
                statuses = offlineScreenRepository.getStatuses(),
                tankTypes = offlineScreenRepository.getTankTypes(),
                types = commonTanksRepository.getTypes(),
            ),
            numbers = Numbers(
                quantity = offlineScreenRepository.getQuantity(),
            ),
        )
    }
}