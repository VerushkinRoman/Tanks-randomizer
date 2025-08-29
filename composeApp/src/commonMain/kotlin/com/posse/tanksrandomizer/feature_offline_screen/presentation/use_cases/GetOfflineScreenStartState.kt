package com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineFilters
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.Numbers

class GetOfflineScreenStartState(
    private val commonTanksRepository: CommonTanksRepository,
    private val offlineRepository: OfflineRepository,
) {
    operator fun invoke(): OfflineScreenState {
        return OfflineScreenState(
            offlineFilters = OfflineFilters(
                tiers = commonTanksRepository.getLevels(),
                experiences = offlineRepository.getExperiences(),
                nations = commonTanksRepository.getNations(),
                pinned = offlineRepository.getPinned(),
                statuses = offlineRepository.getStatuses(),
                tankTypes = offlineRepository.getTankTypes(),
                types = commonTanksRepository.getTypes(),
            ),
            numbers = Numbers(
                quantity = offlineRepository.getQuantity(),
            ),
        )
    }
}