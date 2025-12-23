package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models

open class AddScreenResult(
    val screens: List<PagedScreen<*>>,
    val emptyScreenPosition: Int? = null
)
