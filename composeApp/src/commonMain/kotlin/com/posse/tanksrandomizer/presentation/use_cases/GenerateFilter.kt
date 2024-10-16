package com.posse.tanksrandomizer.presentation.use_cases

import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.repository.model.ItemInfo
import com.posse.tanksrandomizer.repository.model.Pinned
import com.posse.tanksrandomizer.repository.model.SingleChoiceObj
import kotlin.random.Random

class GenerateFilter {
    operator fun invoke(state: MainState): MainState? {
        val newState = state.copy(
            levels = getItems(state.levels) ?: return null,
            experiences = getItems(state.experiences) ?: return null,
            nations = getItems(state.nations) ?: return null,
            pinned = getPinned(state.pinned),
            statuses = getItems(state.statuses) ?: return null,
            tankTypes = getItems(state.tankTypes) ?: return null,
            types = getItems(state.types) ?: return null,
        )

        return newState
    }

    private fun <T : ItemInfo<T>> getItems(items: List<T>): List<T>? {
        return items
            .find { it is SingleChoiceObj }?.selected
            ?.let { singleChoice ->
                if (singleChoice) {
                    val randomItem = items
                        .filter { it.selected && it !is SingleChoiceObj }
                        .randomOrNull()

                    items.map { item ->
                        if (item == randomItem) {
                            item.copy(
                                selected = true,
                                random = true
                            )
                        } else {
                            item.copy(
                                selected = item.selected,
                                random = false
                            )
                        }
                    }
                } else {
                    items.map { level ->
                        if (level.selected) {
                            level.copy(
                                selected = true,
                                random = Random.nextBoolean()
                            )
                        } else {
                            level.copy(
                                selected = false,
                                random = false
                            )
                        }
                    }
                }
            }
    }

    private fun getPinned(pinned: Pinned): Pinned {
        return if (!pinned.selected) {
            pinned.copy(selected = false, random = false)
        } else {
            pinned.copy(selected = true, random = Random.nextBoolean())
        }
    }
}