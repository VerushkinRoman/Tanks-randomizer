package com.posse.tanksrandomizer.presentation.use_cases

import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.repository.SettingsRepository
import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus

class GetState(
    private val repository: SettingsRepository
) {
    operator fun invoke(currentState: MainState): MainState {
        return currentState.copy(
            levels = currentState.levels.changeItems(repository.levels),
            experiences = currentState.experiences.changeItems(repository.experiences),
            nations = currentState.nations.changeItems(repository.nations),
            pinned = currentState.pinned.changeItems(repository.pinned),
            statuses = currentState.statuses.changeItems(repository.statuses),
            tankTypes = currentState.tankTypes.changeItems(repository.tankTypes),
            types = currentState.types.changeItems(repository.types),
            quantity = repository.quantity
        )
    }

    private fun <T : ItemStatus<T>> List<T>.changeItems(newItems: List<T>): List<T> {
        return map { item ->
            val newItem = newItems.find { it::class == item::class } ?: return@map item
            item.copy(selected = newItem.selected, random = newItem.random)
        }
    }
}