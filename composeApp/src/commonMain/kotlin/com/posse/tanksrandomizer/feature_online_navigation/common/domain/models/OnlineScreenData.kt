package com.posse.tanksrandomizer.feature_online_navigation.common.domain.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class OnlineScreenData @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    val name: String,
    val position: Int,
    val selected: Boolean = false,
    val accountId: Int? = null,
)
