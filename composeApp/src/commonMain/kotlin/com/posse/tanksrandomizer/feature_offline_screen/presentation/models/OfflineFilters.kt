package com.posse.tanksrandomizer.feature_offline_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.TankType

data class OfflineFilters(
    val tiers: List<Tier> = Tier.defaultValues,
    val experiences: List<Experience> = Experience.defaultValues,
    val nations: List<Nation> = Nation.defaultValues,
    val pinned: List<Pinned> = Pinned.defaultValues,
    val statuses: List<Status> = Status.defaultValues,
    val tankTypes: List<TankType> = TankType.defaultValues,
    val types: List<Type> = Type.defaultValues,
) {
    fun <T : ItemStatus<T>> changeItem(item: T): OfflineFilters {
        return copy(
            tiers = if (item is Tier) tiers.changeSelected(item) else tiers,
            experiences = if (item is Experience) experiences.changeSelected(item) else experiences,
            nations = if (item is Nation) nations.changeSelected(item) else nations,
            pinned = if (item is Pinned) pinned.changeSelected(item) else pinned,
            statuses = if (item is Status) statuses.changeSelected(item) else statuses,
            tankTypes = if (item is TankType) tankTypes.changeSelected(item) else tankTypes,
            types = if (item is Type) types.changeSelected(item) else types,
        )
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
