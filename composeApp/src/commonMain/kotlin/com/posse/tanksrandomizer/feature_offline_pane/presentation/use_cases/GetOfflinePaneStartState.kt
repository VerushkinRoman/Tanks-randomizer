package com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonRepository
import com.posse.tanksrandomizer.feature_offline_pane.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineFilters
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneState
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.Numbers

class GetOfflinePaneStartState(
    private val commonRepository: CommonRepository,
    private val offlineRepository: OfflineRepository,
) {
    operator fun invoke(): OfflinePaneState {
        return OfflinePaneState(
            offlineFilters = OfflineFilters(
                levels = commonRepository.getLevels(),
                experiences = offlineRepository.getExperiences(),
                nations = commonRepository.getNations(),
                pinned = offlineRepository.getPinned(),
                statuses = offlineRepository.getStatuses(),
                tankTypes = commonRepository.getTankTypes(),
                types = commonRepository.getTypes(),
            ),
            numbers = Numbers(
                quantity = offlineRepository.getQuantity(),
            ),
        )
    }
}