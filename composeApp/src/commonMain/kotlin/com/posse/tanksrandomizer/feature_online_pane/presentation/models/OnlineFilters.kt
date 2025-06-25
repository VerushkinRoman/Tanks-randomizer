package com.posse.tanksrandomizer.feature_online_pane.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_online_pane.domain.models.OnlineFilterObjects.Mastery

data class OnlineFilters(
    val levels: List<Level> = Level.defaultValues,
    val nations: List<Nation> = Nation.defaultValues,
    val tankTypes: List<TankType> = TankType.defaultValues,
    val types: List<Type> = Type.defaultValues,
    val mastery: List<Mastery> = Mastery.defaultValues,
) {
    fun <T : ItemStatus<T>> changeItem(item: T): OnlineFilters {
        return copy(
            levels = if (item is Level) levels.changeSelected(item) else levels,
            nations = if (item is Nation) nations.changeSelected(item) else nations,
            tankTypes = if (item is TankType) tankTypes.changeSelected(item) else tankTypes,
            types = if (item is Type) types.changeSelected(item) else types,
            mastery = if (item is Mastery) mastery.changeSelected(item) else mastery,
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

val OnlineFilters.isDefault: Boolean
    get() {
        return this == OnlineFilters()
    }

fun OnlineFilters.reverseSelected(): OnlineFilters {
    return copy(
        levels = levels.map { it.unselect() },
        types = types.map { it.unselect() },
        tankTypes = tankTypes.map { it.unselect() },
        nations = nations.map { it.unselect() },
        mastery = mastery.map { it.unselect() },
    )
}
