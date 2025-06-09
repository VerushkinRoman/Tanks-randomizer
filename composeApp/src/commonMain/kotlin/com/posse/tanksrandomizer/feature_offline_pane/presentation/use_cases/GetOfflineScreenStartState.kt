package com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.FilterRepository
import com.posse.tanksrandomizer.feature_offline_pane.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.Filters
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineScreenState
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.Numbers

class GetOfflineScreenStartState(
    private val filterRepository: FilterRepository,
    private val offlineRepository: OfflineRepository,
) {
    operator fun invoke(): OfflineScreenState {
        return OfflineScreenState(
            filters = Filters(
                levels = filterRepository.getLevels(),
                experiences = filterRepository.getExperiences(),
                nations = filterRepository.getNations(),
                pinned = filterRepository.getPinned(),
                statuses = filterRepository.getStatuses(),
                tankTypes = filterRepository.getTankTypes(),
                types = filterRepository.getTypes(),
            ),
            numbers = Numbers(
                quantity = offlineRepository.getQuantity(),
            ),
        )
    }
}