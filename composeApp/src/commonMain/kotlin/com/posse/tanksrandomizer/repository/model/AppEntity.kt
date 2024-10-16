package com.posse.tanksrandomizer.repository.model

import org.jetbrains.compose.resources.DrawableResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.exp_birthday
import tanks_randomizer.composeapp.generated.resources.exp_x2
import tanks_randomizer.composeapp.generated.resources.exp_x5
import tanks_randomizer.composeapp.generated.resources.level_1
import tanks_randomizer.composeapp.generated.resources.level_10
import tanks_randomizer.composeapp.generated.resources.level_2
import tanks_randomizer.composeapp.generated.resources.level_3
import tanks_randomizer.composeapp.generated.resources.level_4
import tanks_randomizer.composeapp.generated.resources.level_5
import tanks_randomizer.composeapp.generated.resources.level_6
import tanks_randomizer.composeapp.generated.resources.level_7
import tanks_randomizer.composeapp.generated.resources.level_8
import tanks_randomizer.composeapp.generated.resources.level_9
import tanks_randomizer.composeapp.generated.resources.nation_china
import tanks_randomizer.composeapp.generated.resources.nation_europe
import tanks_randomizer.composeapp.generated.resources.nation_france
import tanks_randomizer.composeapp.generated.resources.nation_germany
import tanks_randomizer.composeapp.generated.resources.nation_great_britain
import tanks_randomizer.composeapp.generated.resources.nation_japan
import tanks_randomizer.composeapp.generated.resources.nation_unknown
import tanks_randomizer.composeapp.generated.resources.nation_usa
import tanks_randomizer.composeapp.generated.resources.nation_ussr
import tanks_randomizer.composeapp.generated.resources.pinned
import tanks_randomizer.composeapp.generated.resources.status_elite
import tanks_randomizer.composeapp.generated.resources.status_not_elite
import tanks_randomizer.composeapp.generated.resources.tank_type_collection
import tanks_randomizer.composeapp.generated.resources.tank_type_common
import tanks_randomizer.composeapp.generated.resources.tank_type_premium
import tanks_randomizer.composeapp.generated.resources.type_heavy
import tanks_randomizer.composeapp.generated.resources.type_light
import tanks_randomizer.composeapp.generated.resources.type_medium
import tanks_randomizer.composeapp.generated.resources.type_td

interface ItemInfo<T> {
    val sort: Int
    val selected: Boolean
    val random: Boolean
    val resId: DrawableResource?
    fun copy(selected: Boolean, random: Boolean): T
}

interface SingleChoiceObj {
    val selected: Boolean
}

sealed interface Level : ItemInfo<Level> {
    data class Level1(
        override val sort: Int = 1,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_1,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Level2(
        override val sort: Int = 2,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_2,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Level3(
        override val sort: Int = 3,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_3,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Level4(
        override val sort: Int = 4,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_4,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Level5(
        override val sort: Int = 5,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_5,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Level6(
        override val sort: Int = 6,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_6,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Level7(
        override val sort: Int = 7,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_7,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Level8(
        override val sort: Int = 8,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_8,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Level9(
        override val sort: Int = 9,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_9,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Level10(
        override val sort: Int = 10,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.level_10,
    ) : Level {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class SingleChoice(
        override val sort: Int = 11,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = null,
    ) : Level, SingleChoiceObj {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    companion object {
        val allValues = listOf(
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
            SingleChoice()
        )
            .sortedBy { it.sort }
    }
}

sealed interface Experience : ItemInfo<Experience> {
    data class X2(
        override val sort: Int = 1,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.exp_x2,
    ) : Experience {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class BDay(
        override val sort: Int = 2,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.exp_birthday,
    ) : Experience {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class X5(
        override val sort: Int = 3,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.exp_x5,
    ) : Experience {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class SingleChoice(
        override val sort: Int = 4,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = null,
    ) : Experience, SingleChoiceObj {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    companion object {
        val allValues = listOf(X2(), BDay(), X5(), SingleChoice()).sortedBy { it.sort }
    }
}

sealed interface Nation : ItemInfo<Nation> {
    data class USA(
        override val sort: Int = 1,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.nation_usa,
    ) : Nation {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Germany(
        override val sort: Int = 2,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.nation_germany,
    ) : Nation {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class USSR(
        override val sort: Int = 3,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.nation_ussr,
    ) : Nation {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class GreatBritain(
        override val sort: Int = 4,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.nation_great_britain,
    ) : Nation {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Japan(
        override val sort: Int = 5,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.nation_japan,
    ) : Nation {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class China(
        override val sort: Int = 6,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.nation_china,
    ) : Nation {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class France(
        override val sort: Int = 7,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.nation_france,
    ) : Nation {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Europe(
        override val sort: Int = 8,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.nation_europe,
    ) : Nation {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Unknown(
        override val sort: Int = 9,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.nation_unknown,
    ) : Nation {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class SingleChoice(
        override val sort: Int = 10,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = null,
    ) : Nation, SingleChoiceObj {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    companion object {
        val allValues = listOf(
            China(),
            Europe(),
            France(),
            Germany(),
            GreatBritain(),
            Japan(),
            Unknown(),
            USA(),
            USSR(),
            SingleChoice()
        )
            .sortedBy { it.sort }
    }
}

sealed interface Pinned : ItemInfo<Pinned> {
    data class Status(
        override val sort: Int = 1,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.pinned,
    ) : Pinned {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    companion object {
        val default = Status()
    }
}

sealed interface Status : ItemInfo<Status> {
    data class NotElite(
        override val sort: Int = 1,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.status_not_elite,
    ) : Status {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Elite(
        override val sort: Int = 2,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.status_elite,
    ) : Status {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class SingleChoice(
        override val sort: Int = 3,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = null,
    ) : Status, SingleChoiceObj {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    companion object {
        val allValues = listOf(Elite(), NotElite(), SingleChoice()).sortedBy { it.sort }
    }
}

sealed interface TankType : ItemInfo<TankType> {
    data class Common(
        override val sort: Int = 1,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.tank_type_common,
    ) : TankType {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Premium(
        override val sort: Int = 2,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.tank_type_premium,
    ) : TankType {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Collection(
        override val sort: Int = 3,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.tank_type_collection,
    ) : TankType {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class SingleChoice(
        override val sort: Int = 4,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = null,
    ) : TankType, SingleChoiceObj {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    companion object {
        val allValues =
            listOf(Collection(), Common(), Premium(), SingleChoice()).sortedBy { it.sort }
    }
}

sealed interface Type : ItemInfo<Type> {
    data class Light(
        override val sort: Int = 1,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.type_light,
    ) : Type {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Medium(
        override val sort: Int = 2,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.type_medium,
    ) : Type {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class Heavy(
        override val sort: Int = 3,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.type_heavy,
    ) : Type {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class TankDestroyer(
        override val sort: Int = 4,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = Res.drawable.type_td,
    ) : Type {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    data class SingleChoice(
        override val sort: Int = 5,
        override val selected: Boolean = true,
        override val random: Boolean = false,
        override val resId: DrawableResource? = null,
    ) : Type, SingleChoiceObj {
        override fun copy(selected: Boolean, random: Boolean) =
            copy(selected = selected, random = random, sort = this.sort)
    }

    companion object {
        val allValues = listOf(Heavy(), Light(), Medium(), TankDestroyer(), SingleChoice())
            .sortedBy { it.sort }
    }
}
