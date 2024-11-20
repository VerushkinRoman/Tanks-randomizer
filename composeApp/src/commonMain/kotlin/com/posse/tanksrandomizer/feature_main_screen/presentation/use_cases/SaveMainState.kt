package com.posse.tanksrandomizer.feature_main_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_main_screen.domain.repository.MainRepository
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.MainState
import kotlinx.coroutines.withContext

class SaveMainState(
    private val repository: MainRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(state: MainState) = withContext(dispatchers.io) {
        repository.setPinned(state.filters.pinned)
        repository.setTypes(state.filters.types)
        repository.setStatuses(state.filters.statuses)
        repository.setTankTypes(state.filters.tankTypes)
        repository.setExperiences(state.filters.experiences)
        repository.setLevels(state.filters.levels)
        repository.setNations(state.filters.nations)
        repository.setQuantity(state.numbers.quantity)
    }
}