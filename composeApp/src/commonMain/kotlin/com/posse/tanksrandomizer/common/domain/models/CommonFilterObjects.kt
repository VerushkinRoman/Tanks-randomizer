package com.posse.tanksrandomizer.common.domain.models

import kotlinx.serialization.Serializable

object CommonFilterObjects {
    interface ItemStatus<T> {
        val name: String
        val sort: Int
        val selected: Boolean
        val random: Boolean
        fun copy(selected: Boolean, random: Boolean = false): T
    }

    fun <T : ItemStatus<T>> List<T>.changeSelected(oldItem: Any): List<T> {
        return map { item ->
            if (item == oldItem) item.copy(selected = !item.selected) else item
        }
    }

    @Serializable
    sealed interface Tier : ItemStatus<Tier> {
        @Serializable
        data class Tier1(
            override val name: String = "Tier1",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Tier2(
            override val name: String = "Tier2",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Tier3(
            override val name: String = "Tier3",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Tier4(
            override val name: String = "Tier4",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Tier5(
            override val name: String = "Tier5",
            override val sort: Int = 5,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Tier6(
            override val name: String = "Tier6",
            override val sort: Int = 6,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Tier7(
            override val name: String = "Tier7",
            override val sort: Int = 7,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Tier8(
            override val name: String = "Tier8",
            override val sort: Int = 8,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Tier9(
            override val name: String = "Tier9",
            override val sort: Int = 9,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Tier10(
            override val name: String = "Tier10",
            override val sort: Int = 10,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Tier {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Tier1(),
                Tier2(),
                Tier3(),
                Tier4(),
                Tier5(),
                Tier6(),
                Tier7(),
                Tier8(),
                Tier9(),
                Tier10(),
            ).sortedBy { it.sort }
        }
    }

    @Serializable
    sealed interface Nation : ItemStatus<Nation> {
        @Serializable
        data class USSR(
            override val name: String = "ussr",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class USA(
            override val name: String = "usa",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Germany(
            override val name: String = "germany",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class UK(
            override val name: String = "uk",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Japan(
            override val name: String = "japan",
            override val sort: Int = 5,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class China(
            override val name: String = "china",
            override val sort: Int = 6,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class France(
            override val name: String = "france",
            override val sort: Int = 7,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class European(
            override val name: String = "european",
            override val sort: Int = 8,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Other(
            override val name: String = "other",
            override val sort: Int = 9,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                USA(),
                Germany(),
                USSR(),
                UK(),
                Japan(),
                China(),
                France(),
                European(),
                Other(),
            ).sortedBy { it.sort }
        }
    }

    @Serializable
    sealed interface Type : ItemStatus<Type> {
        @Serializable
        data class Light(
            override val name: String = "lightTank",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Medium(
            override val name: String = "mediumTank",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class Heavy(
            override val name: String = "heavyTank",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        @Serializable
        data class TankDestroyer(
            override val name: String = "AT-SPG",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Light(),
                Medium(),
                Heavy(),
                TankDestroyer(),
            ).sortedBy { it.sort }
        }
    }
}
