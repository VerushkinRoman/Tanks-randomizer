package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ScreenMetadata @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String = Uuid.random().toString(),
    val name: String?,
    val position: Int,
    val selected: Boolean
)

interface PagedScreen<out T> {
    val metadata: ScreenMetadata
    val additionalData: T

    fun withMetadata(metadata: ScreenMetadata): PagedScreen<T>
}
