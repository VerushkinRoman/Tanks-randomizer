package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models

import com.posse.tanksrandomizer.common.domain.models.Token

sealed interface PagedOnlineScreensEvent {
    class OnSuccessLogin(val id: String, val name: String, val token: Token) : PagedOnlineScreensEvent
}
