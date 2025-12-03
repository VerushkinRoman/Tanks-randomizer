package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models

import com.posse.tanksrandomizer.common.domain.models.Token

sealed interface PagedOnlineScreensEvent {
    data object ClearAction : PagedOnlineScreensEvent
    data object AddScreenPressed : PagedOnlineScreensEvent
    class OnlineScreenSelected(val id: String) : PagedOnlineScreensEvent
    class EditMenuCalled(val id: String) : PagedOnlineScreensEvent
    data object EditMenuDismissed : PagedOnlineScreensEvent
    class MoveRight(val id: String) : PagedOnlineScreensEvent
    class MoveLeft(val id: String) : PagedOnlineScreensEvent
    class OnNameChanged(val id: String, val name: String) : PagedOnlineScreensEvent
    class OnNameEdited(val name: String) : PagedOnlineScreensEvent
    class ShowNameEditField(val id: String) : PagedOnlineScreensEvent
    data object OnNameEditDismissed : PagedOnlineScreensEvent
    class RemoveScreenPressed(val id: String) : PagedOnlineScreensEvent
    class OnSuccessLogin(val id: String, val name: String, val token: Token) : PagedOnlineScreensEvent
}
