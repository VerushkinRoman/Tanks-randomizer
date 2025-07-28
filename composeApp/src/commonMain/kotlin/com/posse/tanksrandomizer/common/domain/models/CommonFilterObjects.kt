package com.posse.tanksrandomizer.common.domain.models

object CommonFilterObjects {
    interface SwitchItem

    interface ItemStatus<T> {
        val name: String
        val sort: Int
        val selected: Boolean
        val random: Boolean
        fun copy(selected: Boolean, random: Boolean = false): T
        fun unselect(): T {
            return copy(
                selected = false,
                random = random,
            )
        }
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

    sealed interface Nation : ItemStatus<Nation> {
        data class USSR(
            override val name: String = "ussr",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class USA(
            override val name: String = "usa",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Germany(
            override val name: String = "germany",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class GreatBritain(
            override val name: String = "uk",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Japan(
            override val name: String = "japan",
            override val sort: Int = 5,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class China(
            override val name: String = "china",
            override val sort: Int = 6,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class France(
            override val name: String = "france",
            override val sort: Int = 7,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Europe(
            override val name: String = "european",
            override val sort: Int = 8,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Unknown(
            override val name: String = "other",
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

    sealed interface Type : ItemStatus<Type> {
        data class Light(
            override val name: String = "lightTank",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Medium(
            override val name: String = "mediumTank",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Heavy(
            override val name: String = "heavyTank",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class TankDestroyer(
            override val name: String = "AT-SPG",
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
