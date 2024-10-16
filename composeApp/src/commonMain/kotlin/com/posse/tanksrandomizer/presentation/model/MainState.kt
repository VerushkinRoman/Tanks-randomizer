package com.posse.tanksrandomizer.presentation.model

import com.posse.tanksrandomizer.repository.model.Experience
import com.posse.tanksrandomizer.repository.model.ItemInfo
import com.posse.tanksrandomizer.repository.model.Level
import com.posse.tanksrandomizer.repository.model.Nation
import com.posse.tanksrandomizer.repository.model.Pinned
import com.posse.tanksrandomizer.repository.model.SingleChoiceObj
import com.posse.tanksrandomizer.repository.model.Status
import com.posse.tanksrandomizer.repository.model.TankType
import com.posse.tanksrandomizer.repository.model.Type
import com.posse.tanksrandomizer.utils.BoxedInt

data class MainState(
    val levels: List<Level> = Level.allValues,
    val experiences: List<Experience> = Experience.allValues,
    val nations: List<Nation> = Nation.allValues,
    val pinned: Pinned = Pinned.default,
    val statuses: List<Status> = Status.allValues,
    val tankTypes: List<TankType> = TankType.allValues,
    val types: List<Type> = Type.allValues,
    val quantity: Int = 1,
    val generatedQuantity: BoxedInt = BoxedInt(1),
)

val MainState.isDefault: Boolean
    get() {
        return levels == Level.allValues
                && experiences == Experience.allValues
                && nations == Nation.allValues
                && pinned == Pinned.default
                && statuses == Status.allValues
                && tankTypes == TankType.allValues
                && types == Type.allValues
    }

fun MainState.reverseSelected(): MainState {
    return copy(
        levels = levels.map { unselect(it) },
        experiences = experiences.map { unselect(it) },
        types = types.map { unselect(it) },
        tankTypes = tankTypes.map { unselect(it) },
        statuses = statuses.map { unselect(it) },
        nations = nations.map { unselect(it) },
        pinned = unselect(pinned),
    )
}

private fun <T : ItemInfo<T>> unselect(value: T): T {
    return if (value is SingleChoiceObj) {
        value
    } else {
        value.copy(selected = false, random = value.random)
    }
}
