package com.posse.tanksrandomizer.navigation.presentation.screens

import kotlinx.serialization.Serializable

@Serializable
data class DeepLinkRoute(
    val params: String? = null
)