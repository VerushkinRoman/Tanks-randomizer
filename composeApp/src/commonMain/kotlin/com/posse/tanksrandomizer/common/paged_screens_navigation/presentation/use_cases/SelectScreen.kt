package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen

class SelectScreen {
    internal operator fun invoke(
        id: String,
        screens: List<PagedScreen<*>>,
    ): List<PagedScreen<*>> {
        return screens
            .map { screen ->
                val newMetadata = screen.metadata.copy(
                    selected = screen.metadata.id == id
                )
                screen.withMetadata(newMetadata)
            }
    }
}
