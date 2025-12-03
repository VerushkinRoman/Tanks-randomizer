package com.posse.tanksrandomizer.feature_online_navigation.common.presentation.models

import kotlinx.serialization.Serializable

@Serializable
data class OnlineScreenNavigationData(
    val id: String,
    val accountId: Int,
)
