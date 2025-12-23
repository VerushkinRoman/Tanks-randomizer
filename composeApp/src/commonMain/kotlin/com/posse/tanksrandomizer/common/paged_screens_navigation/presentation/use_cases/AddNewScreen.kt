package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases

import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.AddScreenResult
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen

abstract class AddNewScreen {
    internal abstract operator fun invoke(screens: List<PagedScreen<*>>): AddScreenResult
}
