package com.posse.tanksrandomizer.feature_main_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.model.FilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.SwitchItem
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.Filters
import kotlinx.coroutines.withContext

class GenerateFilter(
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(filters: Filters): Filters = withContext(dispatchers.io) {
        filters.copy(
            levels = getItems(filters.levels),
            experiences = getItems(filters.experiences),
            nations = getItems(filters.nations),
            pinned = getItems(filters.pinned),
            statuses = getItems(filters.statuses),
            tankTypes = getItems(filters.tankTypes),
            types = getItems(filters.types),
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