package com.posse.tanksrandomizer.presentation.use_cases

import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.repository.model.FilterObjects.SwitchItem
import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus

class GenerateFilter {
    operator fun invoke(state: MainState): MainState {
        return state.copy(
            levels = getItems(state.levels),
            experiences = getItems(state.experiences),
            nations = getItems(state.nations),
            pinned = getItems(state.pinned),
            statuses = getItems(state.statuses),
            tankTypes = getItems(state.tankTypes),
            types = getItems(state.types),
        )
    }

    private fun <T : ItemStatus<T>> getItems(items: List<T>): List<T> {
        val randomItem = items
            .filter { it.selected && it !is SwitchItem }
            .randomOrNull()

        return items.map { item ->
            if (item == randomItem) {
                item.copy(
                    selected = true,
                    random = true
                )
            } else {
                item.copy(
                    selected = item.selected,
                )
            }
        }
    }
}