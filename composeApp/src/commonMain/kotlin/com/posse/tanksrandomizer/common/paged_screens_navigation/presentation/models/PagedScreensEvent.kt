package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models

sealed interface PagedScreensEvent {
    data object ClearAction : PagedScreensEvent
    data object AddScreenPressed : PagedScreensEvent
    class ScreenSelected(val id: String) : PagedScreensEvent
    class MoveRight(val id: String) : PagedScreensEvent
    class MoveLeft(val id: String) : PagedScreensEvent
    class RemoveScreenPressed(val id: String) : PagedScreensEvent
}
