package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models

sealed interface PagedScreensAction {
    class CantAddScreens(val emptyScreenPosition: Int) : PagedScreensAction
}
