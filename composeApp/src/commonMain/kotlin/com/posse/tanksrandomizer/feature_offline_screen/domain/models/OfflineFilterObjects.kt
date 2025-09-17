package com.posse.tanksrandomizer.feature_offline_screen.domain.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem

object OfflineFilterObjects {
    sealed interface Experience : ItemStatus<Experience> {
        data class X2(
            override val name: String = "X2",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Experience {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class BDay(
            override val name: String = "BDay",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Experience {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class X5(
            override val name: String = "X5",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Experience {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class ExperienceSwitch(
            override val name: String = "ExperienceSwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Experience, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                X2(),
                BDay(),
                X5(),
                ExperienceSwitch(),
            ).sortedBy { it.sort }
        }
    }

    sealed interface TankType : ItemStatus<TankType> {
        data class Common(
            override val name: String = "Common",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : TankType {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Premium(
            override val name: String = "Premium",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : TankType {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Collection(
            override val name: String = "Collection",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : TankType {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class TankTypeSwitch(
            override val name: String = "TankTypeSwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : TankType, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Common(),
                Premium(),
                Collection(),
                TankTypeSwitch(),
            ).sortedBy { it.sort }
        }
    }

    sealed interface Pinned : ItemStatus<Pinned> {
        data class Status(
            override val name: String = "Status",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Pinned {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class PinnedSwitch(
            override val name: String = "PinnedSwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Pinned, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Status(),
                PinnedSwitch(),
            ).sortedBy { it.sort }
        }
    }

    sealed interface Status : ItemStatus<Status> {
        data class NotElite(
            override val name: String = "NotElite",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Status {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Elite(
            override val name: String = "Elite",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Status {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class EliteSwitch(
            override val name: String = "EliteSwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Status, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                NotElite(),
                Elite(),
                EliteSwitch(),
            ).sortedBy { it.sort }
        }
    }

    sealed interface MarkCount : ItemStatus<MarkCount> {
        data class NoMarks(
            override val name: String = "NoMarks",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : MarkCount {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Mark1(
            override val name: String = "Mark1",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : MarkCount {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Mark2(
            override val name: String = "Mark2",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : MarkCount {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Mark3(
            override val name: String = "Mark3",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : MarkCount {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class MarkSwitch(
            override val name: String = "MarkSwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : MarkCount, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                NoMarks(),
                Mark1(),
                Mark2(),
                Mark3(),
                MarkSwitch(),
            ).sortedBy { it.sort }
        }
    }
}
