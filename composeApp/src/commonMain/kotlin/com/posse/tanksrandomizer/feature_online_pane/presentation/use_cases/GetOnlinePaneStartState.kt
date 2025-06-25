package com.posse.tanksrandomizer.feature_online_pane.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.repository.CommonRepository
import com.posse.tanksrandomizer.common.domain.utils.BoxedInt
import com.posse.tanksrandomizer.feature_online_pane.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_pane.domain.repository.OnlineRepository
import com.posse.tanksrandomizer.feature_online_pane.presentation.models.OnlineFilters
import com.posse.tanksrandomizer.feature_online_pane.presentation.models.OnlinePaneState

class GetOnlinePaneStartState(
    private val commonRepository: CommonRepository,
    private val onlineRepository: OnlineRepository,
) {
    operator fun invoke(): OnlinePaneState {
        val onlineFilters = OnlineFilters(
            levels = commonRepository.getLevels(),
            nations = commonRepository.getNations(),
            tankTypes = commonRepository.getTankTypes(),
            types = commonRepository.getTypes(),
            mastery = onlineRepository.getMastery()
        )

        val tanksInGarage = onlineRepository.getTanksInGarage().orEmpty()

        val tanksByFilter = getTanksByFilter(filters = onlineFilters, tanksInGarage = tanksInGarage)

        return OnlinePaneState(
            onlineFilters = onlineFilters,
            tanksInGarage = tanksInGarage,
            tanksByFilter = tanksByFilter,
            selectedTank = onlineRepository.getSelectedTank(),
            numberOfFilteredTanks = BoxedInt(tanksByFilter.size),
            token = onlineRepository.getToken(),
            lastAccountUpdated = onlineRepository.getLastAccountUpdated(),
        )
    }

    private fun getTanksByFilter(filters: OnlineFilters, tanksInGarage: List<Tank>): List<Tank> {
        return tanksInGarage.filter { tank ->
            tank.level.name in filters.levels.filter { it.selected }.map { it.name }
                    && tank.nation.name in filters.nations.filter { it.selected }.map { it.name }
                    && tank.tankType.name in filters.tankTypes.filter { it.selected }.map { it.name }
                    && tank.type.name in filters.types.filter { it.selected }.map { it.name }
                    && tank.mastery.name in filters.mastery.filter { it.selected }.map { it.name }
        }
    }
}