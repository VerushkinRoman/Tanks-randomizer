package com.posse.tanksrandomizer.repository.model

object FilterObjects {
    interface SwitchItem

    interface ItemStatus<T> {
        val name: String
        val sort: Int
        val selected: Boolean
        val random: Boolean
        fun copy(selected: Boolean, random: Boolean = false): T
    }

    sealed interface Level : ItemStatus<Level> {
        data class Level1(
            override val name: String = "Level1",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level2(
            override val name: String = "Level2",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level3(
            override val name: String = "Level3",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level4(
            override val name: String = "Level4",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level5(
            override val name: String = "Level5",
            override val sort: Int = 5,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level6(
            override val name: String = "Level6",
            override val sort: Int = 6,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level7(
            override val name: String = "Level7",
            override val sort: Int = 7,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level8(
            override val name: String = "Level8",
            override val sort: Int = 8,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level9(
            override val name: String = "Level9",
            override val sort: Int = 9,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level10(
            override val name: String = "Level10",
            override val sort: Int = 10,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class LevelSwitch(
            override val name: String = "LevelSwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Level1(),
                Level2(),
                Level3(),
                Level4(),
                Level5(),
                Level6(),
                Level7(),
                Level8(),
                Level9(),
                Level10(),
                LevelSwitch(),
            ).sortedBy { it.sort }
        }
    }

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

    sealed interface Nation : ItemStatus<Nation> {
        data class USA(
            override val name: String = "USA",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Germany(
            override val name: String = "Germany",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class USSR(
            override val name: String = "USSR",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class GreatBritain(
            override val name: String = "GreatBritain",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Japan(
            override val name: String = "Japan",
            override val sort: Int = 5,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class China(
            override val name: String = "China",
            override val sort: Int = 6,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class France(
            override val name: String = "France",
            override val sort: Int = 7,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Europe(
            override val name: String = "Europe",
            override val sort: Int = 8,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Unknown(
            override val name: String = "Unknown",
            override val sort: Int = 9,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class NationSwitch(
            override val name: String = "NationSwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                USA(),
                Germany(),
                USSR(),
                GreatBritain(),
                Japan(),
                China(),
                France(),
                Europe(),
                Unknown(),
                NationSwitch(),
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

        data class StatusSwitch(
            override val name: String = "StatusSwitch",
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
                StatusSwitch(),
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

    sealed interface Type : ItemStatus<Type> {
        data class Light(
            override val name: String = "Light",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Medium(
            override val name: String = "Medium",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Heavy(
            override val name: String = "Heavy",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class TankDestroyer(
            override val name: String = "TankDestroyer",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class TypeSwitch(
            override val name: String = "TypeSwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Light(),
                Medium(),
                Heavy(),
                TankDestroyer(),
                TypeSwitch(),
            ).sortedBy { it.sort }
        }
    }
}
