package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineFilters
import kotlinx.coroutines.withContext

class FilterTanks(
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(tanks: List<Tank>, filters: OnlineFilters): List<Tank> {
        return withContext(dispatchers.io) {
            tanks.filter { tank ->
                val typeMatch = filters.types
                    .filter { it.selected }
                    .any { it.name == tank.typeName }
                        || filters.types.none { it.selected }

                val nationMatch = filters.nations
                    .filter { it.selected }
                    .any { it.name == tank.nationName }
                        || filters.nations.none { it.selected }

                val tierMatch = filters.tiers
                    .filter { it.selected }
                    .any { it.sort == tank.tier }
                        || filters.tiers.none { it.selected }

                val masteryMatch = filters.mastery
                    .filter { it.selected }
                    .any { it.sort == tank.mastery }
                        || filters.mastery.none { it.selected }

                val premiumMatch = filters.premium
                    .filter { it.selected }
                    .any { filterPremium ->
                        when (filterPremium) {
                            is Premium.Common -> !tank.isPremium
                            is Premium.IsPremium -> tank.isPremium
                        }
                    } || filters.premium.none { it.selected }

                typeMatch && nationMatch && tierMatch && premiumMatch && masteryMatch
            }
        }
    }
}
