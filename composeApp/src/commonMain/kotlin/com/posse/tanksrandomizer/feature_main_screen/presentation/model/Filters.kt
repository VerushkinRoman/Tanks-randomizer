package com.posse.tanksrandomizer.feature_main_screen.presentation.model

import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Experience
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Level
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Status
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.SwitchItem
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Type

data class Filters(
    val levels: List<Level> = Level.defaultValues,
    val experiences: List<Experience> = Experience.defaultValues,
    val nations: List<Nation> = Nation.defaultValues,
    val pinned: List<Pinned> = Pinned.defaultValues,
    val statuses: List<Status> = Status.defaultValues,
    val tankTypes: List<TankType> = TankType.defaultValues,
    val types: List<Type> = Type.defaultValues,
) {
    fun <T : ItemStatus<T>> changeItem(item: T): Filters {
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

val Filters.isDefault: Boolean
    get() {
        return this == Filters()
    }

fun Filters.reverseSelected(): Filters {
    return copy(
        levels = levels.map { unselect(it) },
        experiences = experiences.map { unselect(it) },
        types = types.map { unselect(it) },
        tankTypes = tankTypes.map { unselect(it) },
        statuses = statuses.map { unselect(it) },
        nations = nations.map { unselect(it) },
        pinned = pinned.map { unselect(it) },
    )
}

private fun <T : ItemStatus<T>> unselect(value: T): T {
    return value.copy(
        selected = false,
        random = value.random,
    )
}