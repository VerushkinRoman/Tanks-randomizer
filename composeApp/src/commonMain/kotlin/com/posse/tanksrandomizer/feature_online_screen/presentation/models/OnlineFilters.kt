package com.posse.tanksrandomizer.feature_online_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank

data class OnlineFilters(
    val tiers: List<Tier> = Tier.defaultValues,
    val nations: List<Nation> = Nation.defaultValues,
    val premium: List<Premium> = Premium.defaultValues,
    val types: List<Type> = Type.defaultValues,
    val mastery: List<Mastery> = Mastery.defaultValues,
) {
    fun <T : ItemStatus<T>> changeItem(item: T): OnlineFilters {
        return copy(
            tiers = if (item is Tier) tiers.changeSelected(item) else tiers,
            nations = if (item is Nation) nations.changeSelected(item) else nations,
            premium = if (item is Premium) premium.changeSelected(item) else premium,
            types = if (item is Type) types.changeSelected(item) else types,
            mastery = if (item is Mastery) mastery.changeSelected(item) else mastery,
        )
    }

    fun markRandomForTank(tank: Tank): OnlineFilters {
        return copy(
            tiers = markRandomForItem(tiers, tank.tier),
            nations = markRandomForItem(nations, tank.nation),
            types = markRandomForItem(types, tank.type),
            premium = markRandomForPremium(premium, tank.isPremium),
            mastery = markRandomForItem(mastery, tank.mastery)
        )
    }

    private fun <T : ItemStatus<T>> markRandomForItem(
        items: List<T>,
        tankItem: Any
    ): List<T> {
        return items.map { filterItem ->
            when {
                filterItem is SwitchItem -> filterItem
                filterItem::class == tankItem::class -> filterItem.copy(random = true, selected = filterItem.selected)
                else -> filterItem.copy(random = false, selected = filterItem.selected)
            }
        }
    }

    private fun markRandomForPremium(
        currentPremium: List<Premium>,
        isPremium: Boolean
    ): List<Premium> {
        return currentPremium.map { filterPremium ->
            when {
                filterPremium is Premium.PremiumSwitch -> filterPremium
                isPremium && filterPremium is Premium.IsPremium ->
                    filterPremium.copy(random = true, selected = filterPremium.selected)
                !isPremium && filterPremium is Premium.Common ->
                    filterPremium.copy(random = true, selected = filterPremium.selected)
                else -> filterPremium.copy(random = false, selected = filterPremium.selected)
            }
        }
    }

    private fun <T : ItemStatus<T>> List<T>.changeSelected(
        oldItem: Any
    ): List<T> {
        val changedItems = if (oldItem is SwitchItem) {
            val allSelected = all { it.selected }
            val anyRandom = any { it.random }
            if (allSelected && !anyRandom) {
                map { item -> item.copy(selected = false) }
            } else {
                map { item -> item.copy(selected = true) }
            }
        } else {
            map { item ->
                if (item == oldItem) item.copy(selected = !item.selected) else item
            }
        }

        val switchItemSelected = changedItems.any { it.selected }

        return changedItems.map { item ->
            if (item is SwitchItem) item.copy(selected = switchItemSelected)
            else item
        }
    }
}
