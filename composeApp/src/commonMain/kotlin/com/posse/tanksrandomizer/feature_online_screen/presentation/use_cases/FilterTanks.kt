package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
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
                    .filter { it.selected && it !is SwitchItem }
                    .any { filterType ->
                        when (filterType) {
                            is Type.Light -> tank.type is Type.Light
                            is Type.Medium -> tank.type is Type.Medium
                            is Type.Heavy -> tank.type is Type.Heavy
                            is Type.TankDestroyer -> tank.type is Type.TankDestroyer
                            else -> false
                        }
                    } || filters.types.none { it.selected && it !is SwitchItem }

                val nationMatch = filters.nations
                    .filter { it.selected && it !is SwitchItem }
                    .any { filterNation ->
                        when (filterNation) {
                            is Nation.USSR -> tank.nation is Nation.USSR
                            is Nation.USA -> tank.nation is Nation.USA
                            is Nation.Germany -> tank.nation is Nation.Germany
                            is Nation.UK -> tank.nation is Nation.UK
                            is Nation.Japan -> tank.nation is Nation.Japan
                            is Nation.China -> tank.nation is Nation.China
                            is Nation.France -> tank.nation is Nation.France
                            is Nation.European -> tank.nation is Nation.European
                            is Nation.Other -> tank.nation is Nation.Other
                            else -> false
                        }
                    } || filters.nations.none { it.selected && it !is SwitchItem }

                val tierMatch = filters.tiers
                    .filter { it.selected && it !is SwitchItem }
                    .any { filterTier ->
                        when (filterTier) {
                            is Tier.Tier1 -> tank.tier is Tier.Tier1
                            is Tier.Tier2 -> tank.tier is Tier.Tier2
                            is Tier.Tier3 -> tank.tier is Tier.Tier3
                            is Tier.Tier4 -> tank.tier is Tier.Tier4
                            is Tier.Tier5 -> tank.tier is Tier.Tier5
                            is Tier.Tier6 -> tank.tier is Tier.Tier6
                            is Tier.Tier7 -> tank.tier is Tier.Tier7
                            is Tier.Tier8 -> tank.tier is Tier.Tier8
                            is Tier.Tier9 -> tank.tier is Tier.Tier9
                            is Tier.Tier10 -> tank.tier is Tier.Tier10
                            else -> false
                        }
                    } || filters.tiers.none { it.selected && it !is SwitchItem }

                val masteryMatch = filters.mastery
                    .filter { it.selected && it !is SwitchItem }
                    .any { filterMastery ->
                        when (filterMastery) {
                            is Mastery.None -> tank.mastery is Mastery.None
                            is Mastery.Class3 -> tank.mastery is Mastery.Class3
                            is Mastery.Class2 -> tank.mastery is Mastery.Class2
                            is Mastery.Class1 -> tank.mastery is Mastery.Class1
                            is Mastery.Master -> tank.mastery is Mastery.Master
                            else -> false
                        }
                    } || filters.mastery.none { it.selected && it !is SwitchItem }

                val premiumMatch = filters.premium
                    .filter { it.selected && it !is SwitchItem }
                    .any { filterPremium ->
                        when (filterPremium) {
                            is Premium.Common -> !tank.isPremium
                            is Premium.IsPremium -> tank.isPremium
                            else -> false
                        }
                    } || filters.premium.none { it.selected && it !is SwitchItem }

                typeMatch && nationMatch && tierMatch && premiumMatch && masteryMatch
            }
        }
    }
}
