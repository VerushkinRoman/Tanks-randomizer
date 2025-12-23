package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases

import androidx.annotation.IntRange
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen

class ChangePosition {
    internal operator fun invoke(
        id: String,
        @IntRange(-1, 1) diff: Int,
        screens: List<PagedScreen<*>>
    ): List<PagedScreen<*>> {
        if (diff == 0 || screens.size < 2) return screens

        val currentIndex = screens.indexOfFirst { it.metadata.id == id }
        if (currentIndex == -1) return screens

        val newIndex = currentIndex + diff
        if (newIndex !in screens.indices) return screens

        return screens
            .toMutableList()
            .apply {
                val element = removeAt(currentIndex)
                add(newIndex, element)
            }
            .mapIndexed { index, screen ->
                val newMetadata = screen.metadata.copy(
                    position = index
                )
                screen.withMetadata(newMetadata)
            }
    }
}
