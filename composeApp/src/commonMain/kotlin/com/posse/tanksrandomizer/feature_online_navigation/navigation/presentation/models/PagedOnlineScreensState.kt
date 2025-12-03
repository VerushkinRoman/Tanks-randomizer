package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens

data class PagedOnlineScreensState(
    val screens: OnlineScreens,
    val editMenuOnElementId: String? = null,
    val nameEditFieldValue: String? = null,
)
