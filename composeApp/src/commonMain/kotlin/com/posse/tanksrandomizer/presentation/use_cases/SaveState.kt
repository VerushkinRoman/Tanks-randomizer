package com.posse.tanksrandomizer.presentation.use_cases

import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.repository.SettingsRepository
import com.posse.tanksrandomizer.utils.Dispatchers
import kotlinx.coroutines.withContext

class SaveState(
    private val repository: SettingsRepository,
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
        repository.setAutorotate(state.rotation.autoRotateEnabled)
        repository.setRotation(state.rotation.rotateDirection)
    }
}