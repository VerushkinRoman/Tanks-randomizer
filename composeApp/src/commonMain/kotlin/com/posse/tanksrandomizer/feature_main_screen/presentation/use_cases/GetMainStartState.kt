package com.posse.tanksrandomizer.feature_main_screen.presentation.use_cases

import com.posse.tanksrandomizer.feature_main_screen.domain.repository.MainRepository
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.Filters
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.MainState
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.Numbers

class GetMainStartState(
    private val repository: MainRepository,
) {
    operator fun invoke(): MainState {
        return MainState(
            filters = Filters(
                levels = repository.getLevels(),
                experiences = repository.getExperiences(),
                nations = repository.getNations(),
                pinned = repository.getPinned(),
                statuses = repository.getStatuses(),
                tankTypes = repository.getTankTypes(),
                types = repository.getTypes(),
            ),
            numbers = Numbers(
                quantity = repository.getQuantity(),
            ),
        )
    }
}