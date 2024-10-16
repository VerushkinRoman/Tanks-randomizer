package com.posse.tanksrandomizer.presentation.use_cases

import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.repository.SettingsRepository

class SaveState(
    private val repository: SettingsRepository
) {
    operator fun invoke(state: MainState) {
        repository.pinned = state.pinned
        repository.types = state.types
        repository.statuses = state.statuses
        repository.tankTypes = state.tankTypes
        repository.experiences = state.experiences
        repository.levels = state.levels
        repository.nations = state.nations
        repository.quantity = state.quantity
    }
}