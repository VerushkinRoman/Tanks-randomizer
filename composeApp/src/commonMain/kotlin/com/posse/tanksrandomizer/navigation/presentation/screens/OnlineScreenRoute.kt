package com.posse.tanksrandomizer.navigation.presentation.screens

import com.posse.tanksrandomizer.common.domain.models.ScreenRoute
import kotlinx.serialization.Serializable

@Serializable
data class OnlineScreenRoute(
    val success: SuccessResponse
) : ScreenRoute
