package com.posse.tanksrandomizer.feature_offline_screen.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.changeSelected
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.MarkCount
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.TankType

data class OfflineFilters(
    val tiers: List<Tier> = Tier.defaultValues,
    val experiences: List<Experience> = Experience.defaultValues,
    val nations: List<Nation> = Nation.defaultValues,
    val pinned: List<Pinned> = Pinned.defaultValues,
    val statuses: List<Status> = Status.defaultValues,
    val marks: List<MarkCount> = MarkCount.defaultValues,
    val tankTypes: List<TankType> = TankType.defaultValues,
    val types: List<Type> = Type.defaultValues,
) {
    val components: List<List<ItemStatus<*>>>
        get() = listOf(tiers, types, marks, experiences, pinned, statuses, tankTypes, nations)

    fun changeItem(item: ItemStatus<*>): OfflineFilters {
        return copy(
            tiers = if (item is Tier) tiers.changeSelected(item) else tiers,
            experiences = if (item is Experience) experiences.changeSelected(item) else experiences,
            nations = if (item is Nation) nations.changeSelected(item) else nations,
            pinned = if (item is Pinned) pinned.changeSelected(item) else pinned,
            statuses = if (item is Status) statuses.changeSelected(item) else statuses,
            marks = if (item is MarkCount) marks.changeSelected(item) else marks,
            tankTypes = if (item is TankType) tankTypes.changeSelected(item) else tankTypes,
            types = if (item is Type) types.changeSelected(item) else types,
        )
    }

    fun clear(): OfflineFilters = changeAll(select = false)

    fun selectAll(): OfflineFilters = changeAll(select = true)

    private fun changeAll(select: Boolean): OfflineFilters {
        return copy(
            tiers = tiers.map { it.copy(selected = select) },
            experiences = experiences.map { it.copy(selected = select) },
            nations = nations.map { it.copy(selected = select) },
            pinned = pinned.map { it.copy(selected = select) },
            statuses = statuses.map { it.copy(selected = select) },
            marks = marks.map { it.copy(selected = select) },
            tankTypes = tankTypes.map { it.copy(selected = select) },
            types = types.map { it.copy(selected = select) },
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OfflineFilters) return false

        return tiers == other.tiers &&
                experiences == other.experiences &&
                nations == other.nations &&
                pinned == other.pinned &&
                statuses == other.statuses &&
                marks == other.marks &&
                tankTypes == other.tankTypes &&
                types == other.types
    }

    override fun hashCode(): Int {
        return listOf(tiers, experiences, nations, pinned, statuses, marks, tankTypes, types)
            .fold(1) { acc, list -> acc * 31 + list.hashCode() }
    }
}
