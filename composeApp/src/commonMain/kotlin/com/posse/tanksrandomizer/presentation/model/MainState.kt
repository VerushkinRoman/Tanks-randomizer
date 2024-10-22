package com.posse.tanksrandomizer.presentation.model

import com.posse.tanksrandomizer.repository.model.FilterObjects.Experience
import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus
import com.posse.tanksrandomizer.repository.model.FilterObjects.Level
import com.posse.tanksrandomizer.repository.model.FilterObjects.Nation
import com.posse.tanksrandomizer.repository.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.repository.model.FilterObjects.Status
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type
import com.posse.tanksrandomizer.utils.BoxedInt

data class MainState(
    val levels: List<Level> = Level.defaultValues,
    val experiences: List<Experience> = Experience.defaultValues,
    val nations: List<Nation> = Nation.defaultValues,
    val pinned: List<Pinned> = Pinned.defaultValues,
    val statuses: List<Status> = Status.defaultValues,
    val tankTypes: List<TankType> = TankType.defaultValues,
    val types: List<Type> = Type.defaultValues,
    val quantity: Int = 1,
    val generatedQuantity: BoxedInt = BoxedInt(1),
)

val MainState.isDefault: Boolean
    get() {
        return levels == Level.defaultValues
                && experiences == Experience.defaultValues
                && nations == Nation.defaultValues
                && pinned == Pinned.defaultValues
                && statuses == Status.defaultValues
                && tankTypes == TankType.defaultValues
                && types == Type.defaultValues
    }

fun MainState.reverseSelected(): MainState {
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