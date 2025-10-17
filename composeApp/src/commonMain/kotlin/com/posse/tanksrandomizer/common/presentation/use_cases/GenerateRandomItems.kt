package com.posse.tanksrandomizer.common.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus

class GenerateRandomItems {
    operator fun <T : ItemStatus<T>> invoke(items: List<T>): List<T> {
        val randomItem = items
            .filter { it.selected }
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
                    random = false
                )
            }
        }
    }
}
