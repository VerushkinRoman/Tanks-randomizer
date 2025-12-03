package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases

import androidx.annotation.IntRange
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens

class ChangePosition {
    operator fun invoke(
        id: String,
        @IntRange(-1, 1) diff: Int,
        screens: OnlineScreens
    ): OnlineScreens {
        if (diff == 0 || screens.size < 2) return screens

        val currentIndex = screens.indexOfFirst { it.id == id }
        if (currentIndex == -1) return screens

        val newIndex = currentIndex + diff
        if (newIndex !in screens.indices) return screens

        return screens.toMutableList().apply {
            val element = removeAt(currentIndex)
            add(newIndex, element)
        }.mapIndexed { index, screen -> screen.copy(position = index) }
    }
}
