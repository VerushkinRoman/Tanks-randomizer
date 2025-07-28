package com.posse.tanksrandomizer.feature_offline_pane.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.TankType

data class OfflineFilters(
    val levels: List<Level> = Level.defaultValues,
    val experiences: List<Experience> = Experience.defaultValues,
    val nations: List<Nation> = Nation.defaultValues,
    val pinned: List<Pinned> = Pinned.defaultValues,
    val statuses: List<Status> = Status.defaultValues,
    val tankTypes: List<TankType> = TankType.defaultValues,
    val types: List<Type> = Type.defaultValues,
) {
    fun <T : ItemStatus<T>> changeItem(item: T): OfflineFilters {
        return copy(
            levels = if (item is Level) levels.changeSelected(item) else levels,
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

val OfflineFilters.isDefault: Boolean
    get() {
        return this == OfflineFilters()
    }

fun OfflineFilters.reverseSelected(): OfflineFilters {
    return copy(
        levels = levels.map { it.unselect() },
        experiences = experiences.map { it.unselect() },
        types = types.map { it.unselect() },
        tankTypes = tankTypes.map { it.unselect() },
        statuses = statuses.map { it.unselect() },
        nations = nations.map { it.unselect() },
        pinned = pinned.map { it.unselect() },
    )
}
