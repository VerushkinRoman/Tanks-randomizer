package com.posse.tanksrandomizer.repository.model

object FilterObjects {
    interface ItemStatus<T> {
        val sort: Int
        val selected: Boolean
        val random: Boolean
        fun copy(selected: Boolean, random: Boolean): T
    }

    sealed interface Level : ItemStatus<Level> {
        data class Level1(
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level2(
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level3(
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level4(
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level5(
            override val sort: Int = 5,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level6(
            override val sort: Int = 6,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level7(
            override val sort: Int = 7,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level8(
            override val sort: Int = 8,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level9(
            override val sort: Int = 9,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Level10(
            override val sort: Int = 10,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Level {
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
            ).sortedBy { it.selected }
        }
    }

    sealed interface Experience : ItemStatus<Experience> {
        data class X2(
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Experience {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class BDay(
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Experience {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class X5(
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Experience {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                X2(),
                BDay(),
                X5(),
            ).sortedBy { it.selected }
        }
    }

    sealed interface Nation : ItemStatus<Nation> {
        data class USA(
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Germany(
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class USSR(
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class GreatBritain(
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Japan(
            override val sort: Int = 5,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class China(
            override val sort: Int = 6,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class France(
            override val sort: Int = 7,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Europe(
            override val sort: Int = 8,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Nation {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Unknown(
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
                GreatBritain(),
                Japan(),
                China(),
                France(),
                Europe(),
                Unknown(),
            ).sortedBy { it.selected }
        }
    }

    sealed interface Pinned : ItemStatus<Pinned> {
        data class Status(
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Pinned {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Status(),
            ).sortedBy { it.selected }
        }
    }

    sealed interface Status : ItemStatus<Status> {
        data class NotElite(
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Status {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Elite(
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Status {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                NotElite(),
                Elite(),
            ).sortedBy { it.selected }
        }
    }

    sealed interface TankType : ItemStatus<TankType> {
        data class Common(
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : TankType {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Premium(
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : TankType {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Collection(
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : TankType {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Common(),
                Premium(),
                Collection(),
            ).sortedBy { it.selected }
        }
    }

    sealed interface Type : ItemStatus<Type> {
        data class Light(
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Medium(
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Heavy(
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Type {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class TankDestroyer(
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
            ).sortedBy { it.selected }
        }
    }
}
