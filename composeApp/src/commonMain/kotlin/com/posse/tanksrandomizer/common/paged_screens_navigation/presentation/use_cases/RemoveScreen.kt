package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen

abstract class RemoveScreen {
    abstract operator fun invoke(id: String, screens: List<PagedScreen<*>>): List<PagedScreen<*>>
}
