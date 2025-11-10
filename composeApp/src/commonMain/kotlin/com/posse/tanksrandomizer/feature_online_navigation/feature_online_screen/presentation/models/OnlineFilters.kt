package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.changeSelected
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank

data class OnlineFilters(
    val tiers: List<Tier> = Tier.defaultValues,
    val nations: List<Nation> = Nation.defaultValues,
    val premium: List<Premium> = Premium.defaultValues,
    val types: List<Type> = Type.defaultValues,
    val mastery: List<Mastery> = Mastery.defaultValues,
) {
    val components: List<List<ItemStatus<*>>>
        get() = listOf(tiers, types, mastery, premium, nations)

    fun changeItem(item: ItemStatus<*>): OnlineFilters {
        return copy(
            tiers = if (item is Tier) tiers.changeSelected(item) else tiers,
            nations = if (item is Nation) nations.changeSelected(item) else nations,
            premium = if (item is Premium) premium.changeSelected(item) else premium,
            types = if (item is Type) types.changeSelected(item) else types,
            mastery = if (item is Mastery) mastery.changeSelected(item) else mastery,
        )
    }

    fun markRandomForTank(tank: Tank): OnlineFilters {
        val tankTier = Tier.defaultValues.find { it.sort == tank.tier }
        val tankNation = Nation.defaultValues.find { it.name == tank.nationName }
        val tankType = Type.defaultValues.find { it.name == tank.typeName }
        val tankMastery = Mastery.defaultValues.find { it.sort == tank.mastery }
        return copy(
            tiers = markRandomForItem(tiers, tankTier),
            nations = markRandomForItem(nations, tankNation),
            types = markRandomForItem(types, tankType),
            premium = markRandomForPremium(premium, tank.isPremium),
            mastery = markRandomForItem(mastery, tankMastery)
        )
    }

    private fun <T : ItemStatus<T>> markRandomForItem(
        items: List<T>,
        tankItem: Any?
    ): List<T> {
        return items.map { filterItem ->
            when {
                tankItem == null -> {
                    filterItem.copy(
                        random = false,
                        selected = filterItem.selected
                    )
                }

                filterItem::class == tankItem::class -> filterItem.copy(
                    random = true,
                    selected = filterItem.selected
                )

                else -> filterItem.copy(
                    random = false,
                    selected = filterItem.selected
                )
            }
        }
    }

    private fun markRandomForPremium(
        currentPremium: List<Premium>,
        isPremium: Boolean
    ): List<Premium> {
        return currentPremium.map { filterPremium ->
            when {
                isPremium && filterPremium is Premium.IsPremium -> {
                    filterPremium.copy(random = true, selected = filterPremium.selected)
                }

                !isPremium && filterPremium is Premium.Common -> {
                    filterPremium.copy(random = true, selected = filterPremium.selected)
                }

                else -> filterPremium.copy(random = false, selected = filterPremium.selected)
            }
        }
    }

    fun clear(): OnlineFilters = changeAll(select = false)

    fun selectAll(): OnlineFilters = changeAll(select = true)

    private fun changeAll(select: Boolean): OnlineFilters {
        return copy(
            tiers = tiers.map { it.copy(selected = select) },
            premium = premium.map { it.copy(selected = select) },
            nations = nations.map { it.copy(selected = select) },
            mastery = mastery.map { it.copy(selected = select) },
            types = types.map { it.copy(selected = select) },
        )
    }
}
